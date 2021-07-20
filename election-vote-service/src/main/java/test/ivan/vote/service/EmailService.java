package test.ivan.vote.service;

public interface EmailService {

    /**
     * 发送选举通知邮件
     * @param electionId
     */
    void send(Long electionId);
}
