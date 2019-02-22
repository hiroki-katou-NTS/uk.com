package nts.uk.file.at.app.export.yearholidaymanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.sys.auth.dom.user.User;

@Stateless
public class OutputYearHolidayManagementExportService extends ExportService<OutputYearHolidayManagementQuery> {

	@Inject
	private OutputYearHolidayManagementGenerator generator;

	@Override
	protected void handle(ExportServiceContext<OutputYearHolidayManagementQuery> context) {
		// 印刷前チェック処理 in UI
		// 画面上に入力されているユーザ固有情報「年休管理表の出力条件」を取得する
		// 事前条件①および②をチェックする UI
		OutputYearHolidayManagementQuery query = context.getQuery();
		
		this.generator.generate(context.getGeneratorContext(), query);
	}

}
