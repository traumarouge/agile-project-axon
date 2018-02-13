package agileproject;

import org.axonframework.eventhandling.EventMessage;

import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * For some reason (maybe a possible Axon bug), after an event tracking processor has performed an event replay, event
 * handlers associated with the tracking processor may be invoked twice when a single event is fetched by the processor
 * thread (see https://groups.google.com/forum/#!topic/axonframework/-ncu0jIH-vM) As a temporary solution, this message
 * handler interceptor should be registered with the event tracking processor, in order to detect and dismiss a double
 * tracked event.
 */
public class DoubleTrackedEventInterceptor implements MessageHandlerInterceptor<EventMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleTrackedEventInterceptor.class);

    private String lastMessageIdentifier;

    @Override
    public Object handle(UnitOfWork<? extends EventMessage<?>> unitOfWork, InterceptorChain interceptorChain)
        throws Exception {

        String messageIdentifier = unitOfWork.getMessage().getIdentifier();
        unitOfWork.onCommit(u -> lastMessageIdentifier = messageIdentifier);

        if (messageIdentifier.equals(lastMessageIdentifier)) {
            LOGGER.warn("Dismiss double tracked event message: {}", lastMessageIdentifier);

            return null;
        }

        return interceptorChain.proceed();
    }
}
