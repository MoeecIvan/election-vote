package test.ivan.vote.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import test.ivan.vote.domain.ElectionVo;
import test.ivan.vote.entity.Election;


@Mapper
public interface ElectionVoConvert {
    ElectionVoConvert INSTANCE = Mappers.getMapper(ElectionVoConvert.class);

    /**
     * Election对象转换成ElectionVo
     * @param election
     * @return
     */
    ElectionVo electionToVo(Election election);

    /**
     * ElectionVo对象转换成Election
     * @param vo
     * @return
     */
    Election voToElection(ElectionVo vo);
}
