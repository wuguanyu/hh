package com.makenv.model.mc.message.runable;


import com.makenv.model.mc.constant.Constants;
import com.makenv.model.mc.message.body.Message;
import com.makenv.model.mc.message.body.MessageWrapper;
import com.makenv.model.mc.message.dispacher.ImessageDispacher;
import com.makenv.model.mc.message.redis.RedisService;


/**
 * Created by wgy on 2017/2/20.
 */
public class MessageListenerRunable implements Runnable {

    private ImessageDispacher messageDispacher;

    private RedisService redisService;

    private MessageWrapper messageWrapper;

    public MessageListenerRunable(MessageWrapper messageWrapper, ImessageDispacher messageDispacher, RedisService redisService) {
        this.messageDispacher = messageDispacher;
        this.messageWrapper = messageWrapper;
        this.redisService = redisService;
    }

    public void setMessageDispacher(ImessageDispacher messageDispacher) {
        this.messageDispacher = messageDispacher;
    }

    public void setMessage(MessageWrapper messageWrapper) {
        this.messageWrapper = messageWrapper;
    }


    @Override
    public void run() {

        boolean flag = messageDispacher.dispacher(messageWrapper.getMessage());

        if(flag) {

            //成功之后从队列中移除
            redisService.ltrim(messageWrapper.getTempQueueName(),1,0);
        }

    }
}