package agileproject;

import org.axonframework.eventhandling.EventMessage;

import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
