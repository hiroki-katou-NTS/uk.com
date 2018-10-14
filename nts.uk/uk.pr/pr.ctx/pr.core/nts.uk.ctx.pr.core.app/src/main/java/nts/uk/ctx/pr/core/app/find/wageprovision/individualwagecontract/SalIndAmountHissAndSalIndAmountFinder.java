package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;


import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class SalIndAmountHissAndSalIndAmountFinder {

    @Inject
    SalIndAmountHisRepository salIndAmountHisRepository;

    @Inject
    SalIndAmountRepository salIndAmountRepository;


    public SalIndAmountHissDto getSalIndAmountHissDto(String perValCode,int cateIndicator,int salBonusCate){
        Optional<SalIndAmountHis> salIndAmountHis=this.salIndAmountHisRepository.getSalIndAmountHisByPerVal(perValCode,cateIndicator,salBonusCate);
        SalIndAmountHissDto salIndAmountHissDto=salIndAmountHis.map(v->new SalIndAmountHissDto(v.getPerValCode(),v.getEmpId(),v.getCateIndicator().value,v.getPeriod().stream().map(f->new PeriodDto(f.getHistoryID(),f.getPeriodYearMonth().start().v(),f.getPeriodYearMonth().end().v())).collect(Collectors.toList()), v.getSalBonusCate().value)).orElse(null);
        return salIndAmountHissDto;
        //return SalIndAmountHissDto.fromDomain(this.salIndAmountHisRepository.getSalIndAmountHisByPerVal(perValCode,cateIndicator,salBonusCate).orElse(null));
    }

    public List<SalIndAmountDto> getSalIndAmountDtosByPerValCode(String perValCode,int cateIndicator,int salBonusCate){
        List<SalIndAmountDto> salIndAmountDtos=new ArrayList<>();
        List<PeriodDto> periodDtos=getSalIndAmountHissDto(perValCode,cateIndicator,salBonusCate).getPeriod();

        int size=periodDtos.size();
        for (int i=0;i<size;i++){
            salIndAmountDtos.add(SalIndAmountDto.fromDomain(this.salIndAmountRepository.getSalIndAmountById(periodDtos.get(i).getHistoryID()).orElse(null)));
        }

        return salIndAmountDtos;
    }




}
