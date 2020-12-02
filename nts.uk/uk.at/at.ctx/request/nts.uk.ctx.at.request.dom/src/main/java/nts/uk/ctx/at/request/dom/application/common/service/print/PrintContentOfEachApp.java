package nts.uk.ctx.at.request.dom.application.common.service.print;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripPrintContent;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.DetailOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.印刷内容を取得する.各申請の印刷内容
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class PrintContentOfEachApp {
	
	/**
	 * 休暇申請の印刷内容
	 */
	
	/**
	 * 勤務変更申請の印刷内容
	 */
	private Optional<PrintContentOfWorkChange> opPrintContentOfWorkChange;
	
	/**
	 * 時間休暇申請の印刷内容
	 */
	
	/**
	 * 打刻申請の印刷内容
	 */
	private Optional<AppStampOutput> opAppStampOutput;
	
	/**
	 * 遅刻早退取消申請の印刷内容
	 */
	private Optional<ArrivedLateLeaveEarlyInfoOutput> opArrivedLateLeaveEarlyInfo;

	/**
	 * 直行直帰申請の印刷内容
	 */
	private Optional<InforGoBackCommonDirectOutput> opInforGoBackCommonDirectOutput;

	/*
	 * 出張申請の印刷内容
	 */
	private Optional<BusinessTripPrintContent> opBusinessTrip;
	
	
	private Optional<DetailOutput> opDetailOutput;
	
	public PrintContentOfEachApp() {
		this.opPrintContentOfWorkChange = Optional.empty();
		this.opAppStampOutput = Optional.empty();
		this.opArrivedLateLeaveEarlyInfo = Optional.empty();
		this.opInforGoBackCommonDirectOutput = Optional.empty();
		this.opBusinessTrip = Optional.empty();
		this.opDetailOutput = Optional.empty();
	}
}
