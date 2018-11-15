package com.biboheart.huip.user.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
	@Autowired
	private ConnectionFactory connectionFactory;

	// 这个名称的通道用于接收RPC的调用
	public final static String userRpcServiceExchangeName = "rpc.exchange.huip.user";
	public final static String userLoadRpcServiceQueueName = "rpc.queue.huip.user.load";
	
	@Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
	
	/**
	 * RPC服务队列
	 * @return
	 */
	@Bean
	public Queue userLoadRpcServiceQueue() {
		return new Queue(userLoadRpcServiceQueueName, false);
	}
	
	/**
	 * RPC服务通道
	 * @return
	 */
	@Bean
	public DirectExchange userRpcServiceExchange() {
		return new DirectExchange(userRpcServiceExchangeName);
	}
	
	/**
	 * 队列绑定到通道
	 * @param userLoadRpcServiceQueue RPC服务队列
	 * @param userUserRpcServiceExchange RPC服务通道
	 * @return
	 */
	@Bean
	@Autowired
	public Binding userLoadRpcServiceQueueBindingUserRpcServiceExchange(Queue userLoadRpcServiceQueue, DirectExchange userRpcServiceExchange) {
		return BindingBuilder.bind(userLoadRpcServiceQueue).to(userRpcServiceExchange).with("load");
	}

}
