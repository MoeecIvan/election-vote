package test.ivan.vote.convert;

import javax.annotation.Generated;
import test.ivan.vote.domain.ElectionVo;
import test.ivan.vote.entity.Election;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-20T01:14:49+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_281 (Oracle Corporation)"
)
public class ElectionVoConvertImpl implements ElectionVoConvert {

    @Override
    public ElectionVo electionToVo(Election election) {
        if ( election == null ) {
            return null;
        }

        ElectionVo electionVo = new ElectionVo();

        electionVo.setElectionId( election.getElectionId() );
        electionVo.setElectionName( election.getElectionName() );
        electionVo.setBrift( election.getBrift() );
        electionVo.setStatus( election.getStatus() );
        electionVo.setTemplateId( election.getTemplateId() );
        electionVo.setStartTime( election.getStartTime() );
        electionVo.setEndTime( election.getEndTime() );
        electionVo.setCreatedTime( election.getCreatedTime() );

        return electionVo;
    }

    @Override
    public Election voToElection(ElectionVo vo) {
        if ( vo == null ) {
            return null;
        }

        Election election = new Election();

        election.setElectionId( vo.getElectionId() );
        election.setElectionName( vo.getElectionName() );
        election.setBrift( vo.getBrift() );
        election.setStatus( vo.getStatus() );
        election.setTemplateId( vo.getTemplateId() );
        election.setStartTime( vo.getStartTime() );
        election.setEndTime( vo.getEndTime() );
        election.setCreatedTime( vo.getCreatedTime() );

        return election;
    }
}
