package com.microservice.crud.message;

import com.microservice.crud.data.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductSendMessage {
    @Value("${crud.rabbitmq.exchange}")
    private String exchange;

    @Value("${crud.rabbitmq.routingkey}")
    private String routingkey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(ProductVO productVO){
        this.rabbitTemplate.convertAndSend(exchange, routingkey, productVO);
    }
}
