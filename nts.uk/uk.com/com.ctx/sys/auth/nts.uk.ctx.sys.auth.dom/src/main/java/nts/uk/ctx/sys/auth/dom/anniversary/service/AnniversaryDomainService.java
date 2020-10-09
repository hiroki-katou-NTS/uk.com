package nts.uk.ctx.sys.auth.dom.anniversary.service;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class AnniversaryDomainService {


    //@Inject
    //個人の記念日情報Repository.今日の記念日を取得する(年月日) repo;

    //TODO \\192.168.50.4\share\500_新構想開発\04_設計\40_ドメイン設計\ドメイン仕様書\UK\bs_基幹\person_個人\個人のインフォメーション\DS_新記念日があるか_ドメイン仕様書.xlsx
      //DomainService 新記念日があるか
//    public boolean isTodayNewAnniversary(){
//        List<AnniversaryNotice> anniversaryNotices = new ArrayList<>();
//        anniversaryNotices = repo.getTodayAnniversary(GeneralDate.today());
//        anniversaryNotices.stream()
//                .filter(item -> item.getAnniversary().compareTo(GeneralDate.today()) == 0)
//                .collect(Collectors.toList());
//        return !anniversaryNotices.isEmpty();
//    }

    //TODO \\192.168.50.4\share\500_新構想開発\04_設計\40_ドメイン設計\ドメイン仕様書\UK\bs_基幹\person_個人\個人のインフォメーション\DS_期間で記念日情報を取得する_ドメイン仕様書.xlsx
    //DomainService 期間で記念日情報を取得する
//    public Map<AnniversaryNotice, Boolean> setFlag(GeneralDate dateStart, GeneralDate dateEnd){
//        List<AnniversaryNotice> anniversaryNotices = new ArrayList<>();
//        if(dateStart.compareTo(dateEnd) == 0 && dateEnd.compareTo(GeneralDate.today()) == 0){
//            anniversaryNotices = repo.getTodayAnniversary(GeneralDate.today());
//        }
//        if(dateStart.compareTo(GeneralDate.today()) != 0 || dateEnd.compareTo(GeneralDate.today()) != 0){
//            if(dateStart.compareTo(dateEnd) == 0) {
//                anniversaryNotices = repo.getAnniversaryByDatePeriod(new DatePeriod(dateStart,dateEnd));
//            } else {
//                GeneralDate date = GeneralDate.ymd(dateStart.year(),12,31);
//                anniversaryNotices = repo.getAnniversaryByDatePeriod(new DatePeriod(dateStart,date));
//                List<AnniversaryNotice> anniversaryNoticeList = repo.getAnniversaryByDatePeriod(new DatePeriod(date.addDays(1),dateEnd));
//                anniversaryNotices.addAll(anniversaryNoticeList);
//            }
//        }
//        return anniversaryNotices.stream().collect(Collectors.toMap()) //TODO
//    }

}
