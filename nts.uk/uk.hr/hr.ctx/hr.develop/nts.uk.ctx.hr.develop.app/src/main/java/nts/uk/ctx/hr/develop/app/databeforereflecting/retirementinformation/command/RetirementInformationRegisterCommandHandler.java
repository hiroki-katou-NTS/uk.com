package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.DataBeforeReflectingPerInfo;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.DataBeforeReflectingRepository;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.NoteRetiment;
import nts.uk.shr.com.context.AppContexts;

public class RetirementInformationRegisterCommandHandler extends CommandHandler<RetirementInformationRegisterCommand> {
	
	@Inject
	private DataBeforeReflectingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<RetirementInformationRegisterCommand> context) {
		RetirementInformationRegisterCommand cmd = context.getCommand();
		// アルゴリズム[定年退職者情報の新規登録_変更]を実行する(thực hiện thuật toán [tạo mới/thay đổi
		// thông tin người nghỉ hưu])
		registerRetirementInformation(cmd.getRetiInfos());
	}

	private void registerRetirementInformation(List<RetiInforRegisInfoCommand> list) {
		// 定年退職者情報リストを個人情報反映前データリストへ変換する(Chuyển đổi RetirementInfoList thành
		// list data trước khi phản ánh thông tin cá nhân)
		
		List<DataBeforeReflectingPerInfo>  listDomain  = createRetirementInfoList(list);
		List<DataBeforeReflectingPerInfo> addListDomain = listDomain.stream().filter(x -> x.getHistoryId() == null)
				.collect(Collectors.toList());
		
		List<DataBeforeReflectingPerInfo> updateListDomain = listDomain.stream().filter(x -> x.getHistoryId() != null)
				.collect(Collectors.toList());
		//個人情報反映前データを変更する (Thay đổi data trước khi phản ánh thông tin cá nhân)
		
		if (!addListDomain.isEmpty()) {
			this.repo.addData(addListDomain);
		}
		
		if (!updateListDomain.isEmpty()) {
			updateListDomain.forEach(x->{x.setHistoryId(IdentifierUtil.randomUniqueId());});
			this.repo.updateData(updateListDomain);
		}
	}

	private List<DataBeforeReflectingPerInfo> createRetirementInfoList(List<RetiInforRegisInfoCommand> list) {
		
		
		return list.stream().map(x->createNew(x)).collect(Collectors.toList());
	}
	
	private DataBeforeReflectingPerInfo  createNew(RetiInforRegisInfoCommand cmd){
		
		String historyId = cmd.getHistoryID();
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String companyCode = AppContexts.user().companyCode();
//		this.pId = pId;
//		this.sId = sId;
//		this.scd = scd;
//		this.workId = workId;
//		this.personName = personName;
//		this.workName = workName;
//		this.requestFlag = requestFlag;
//		this.registerDate = registerDate;
//		this.releaseDate = releaseDate;
//		this.onHoldFlag = onHoldFlag;
//		this.stattus = stattus;
//		this.histId_Refer = histId_Refer;
//		this.date_01 = date_01;
//		this.date_02 = date_02;
//		this.date_03 = date_03;
//		this.date_04 = date_04;
//		this.date_05 = date_05;
//		this.date_06 = date_06;
//		this.date_07 = date_07;
//		this.date_08 = date_08;
//		this.date_09 = date_09;
//		this.date_10 = date_10;
//		this.int_01 = int_01;
//		this.int_02 = int_02;
//		this.int_03 = int_03;
//		this.int_04 = int_04;
//		this.int_05 = int_05;
//		this.int_06 = int_06;
//		this.int_07 = int_07;
//		this.int_08 = int_08;
//		this.int_09 = int_09;
//		this.int_10 = int_10;
//		this.int_11 = int_11;
//		this.int_12 = int_12;
//		this.int_13 = int_13;
//		this.int_14 = int_14;
//		this.int_15 = int_15;
//		this.int_16 = int_16;
//		this.int_17 = int_17;
//		this.int_18 = int_18;
//		this.int_19 = int_19;
//		this.int_20 = int_20;
//		this.num_01 = num_01;
//		this.num_02 = num_02;
//		this.num_03 = num_03;
//		this.num_04 = num_04;
//		this.num_05 = num_05;
//		this.num_06 = num_06;
//		this.num_07 = num_07;
//		this.num_08 = num_08;
//		this.num_09 = num_09;
//		this.num_10 = num_10;
//		this.num_11 = num_11;
//		this.num_12 = num_12;
//		this.num_13 = num_13;
//		this.num_14 = num_14;
//		this.num_15 = num_15;
//		this.num_16 = num_16;
//		this.num_17 = num_17;
//		this.num_18 = num_18;
//		this.num_19 = num_19;
//		this.num_20 = num_20;
//		this.select_code_01 = select_code_01;
//		this.select_code_02 = select_code_02;
//		this.select_code_03 = select_code_03;
//		this.select_code_04 = select_code_04;
//		this.select_code_05 = select_code_05;
//		this.select_code_06 = select_code_06;
//		this.select_code_07 = select_code_07;
//		this.select_code_08 = select_code_08;
//		this.select_code_09 = select_code_09;
//		this.select_code_10 = select_code_10;
//		this.select_id_01 = select_id_01;
//		this.select_id_02 = select_id_02;
//		this.select_id_03 = select_id_03;
//		this.select_id_04 = select_id_04;
//		this.select_id_05 = select_id_05;
//		this.select_id_06 = select_id_06;
//		this.select_id_07 = select_id_07;
//		this.select_id_08 = select_id_08;
//		this.select_id_09 = select_id_09;
//		this.select_id_10 = select_id_10;
//		this.select_name_01 = select_name_01;
//		this.select_name_02 = select_name_02;
//		this.select_name_03 = select_name_03;
//		this.select_name_04 = select_name_04;
//		this.select_name_05 = select_name_05;
//		this.select_name_06 = select_name_06;
//		this.select_name_07 = select_name_07;
//		this.select_name_08 = select_name_08;
//		this.select_name_09 = select_name_09;
//		this.select_name_10 = select_name_10;
//		this.str_01 = new NoteRetiment(str_01);
//		this.str_02 = new NoteRetiment(str_02);
//		this.str_03 = new NoteRetiment(str_03);
//		this.str_04 = new NoteRetiment(str_04);
//		this.str_05 = new NoteRetiment(str_05);
//		this.str_06 = new NoteRetiment(str_06);
//		this.str_07 = new NoteRetiment(str_07);
//		this.str_08 = new NoteRetiment(str_08);
//		this.str_09 = new NoteRetiment(str_09);
//		this.str_10 = str_10;
		
		return new DataBeforeReflectingPerInfo();
	}

}
