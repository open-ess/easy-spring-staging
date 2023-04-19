package com.ess.core.model.convert;

import com.ess.core.model.Page;
import com.ess.core.model.dto.AbstractDTO;
import com.ess.core.model.vo.AbstractAddVO;
import com.ess.core.model.vo.AbstractDetailVO;
import com.ess.core.model.vo.AbstractEditVO;
import com.ess.core.model.vo.AbstractListVO;

import java.util.List;

public interface ControllerConverter<K, DV extends AbstractDetailVO<K>, LV extends AbstractListVO<K>, AV extends AbstractAddVO<Void>, EV extends AbstractEditVO<K>,DTO extends AbstractDTO<K> >{
    DTO dvToDto(DV v);
    List<DTO> dvToDto(List<DV> v);
    DTO lvToDto(LV v);
    List<DTO> lvToDto(List<LV> v);
    DTO avToDto(AV v);
    List<DTO> avToDto(List<AV> v);
    DTO evToDto(EV v);
    List<DTO> evToDto(List<EV> v);
    DV dtoToDv(DTO dto);
    List<DV> dtoToDv(List<DTO> dto);
    LV dtoToLv(DTO dto);
    List<LV> dtoToLv(List<DTO> dto);
    AV dtoToAv(DTO dto);
    List<AV> dtoToAv(List<DTO> dto);
    EV dtoToEv(DTO dto);
    List<EV> dtoToEv(List<DTO> dto);

    default Page<LV> dtoToLv(Page<DTO> dto){
        Page<LV> pl = null;
        if(dto!=null){
            List<LV> pageList = dtoToLv(dto.getItems());
            pl = new Page<>(dto, pageList);
        }
        return pl;
    }

}
