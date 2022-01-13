package com.micoservice.payment.message;

import com.micoservice.payment.data.vo.ProductVO;
import com.micoservice.payment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductReceiveMessage {
    private final ProductRepository productRepository;

    @RabbitListener(queues = {"${crud.rabbitmq.queue}"})
    public void receive(@Payload ProductVO productVO){
        productRepository.save(ProductVO.create(productVO));
    }
}
