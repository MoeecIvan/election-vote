package test.ivan.vote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import test.ivan.vote.entity.VoteRule;
import test.ivan.vote.mapper.VoteRuleMapper;
import test.ivan.vote.service.VoteRuleService;

/**
 * @author Ivan
 */
@Service
public class VoteRuleServiceImpl extends ServiceImpl<VoteRuleMapper, VoteRule> implements VoteRuleService {

}
