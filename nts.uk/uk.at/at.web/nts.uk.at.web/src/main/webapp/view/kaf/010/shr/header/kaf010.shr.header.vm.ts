module nts.uk.at.view.kaf010.shr.header.viewmodel {
    const template = `
    <div data-bind="with: $parent">
	<div data-bind="if: true">
		<div class="cf valign-center control-group"
			data-bind="style: {padding: '10px 10px 10px 12px'}"
			style="margin-left: -2px; background: #f8efd4; width: 780px;">
			<!--A2_7 時間外労働ラベル-->
			<div class="pull-left" data-bind="text: $i18n('KAF010_23')"></div>
			<div class="pull-left">
				<table id="kaf005_overtimeAgreement_table">
					<colgroup>
						<col width="85px" />
						<col width="70px" />
						<col width="70px" />
						<col width="70px" />
						<col width="70px" />
					</colgroup>
					<thead>
						<tr>
						<!--B2_9 年月ラベル-->
						<th class="kaf005_overtimeAgreement_header"
							data-bind="text: $i18n('KAF010_171')"></th>
						<!--B2_10  限度ラベル-->
						<th class="kaf005_overtimeAgreement_header"
							data-bind="text: $i18n('KAF010_172')"></th>
						<!--B2_11 実績ラベル-->
						<th class="kaf005_overtimeAgreement_header"
							data-bind="text: $i18n('KAF010_173')"></th>
						</tr>
					</thead>
					<tbody data-bind="foreach: overTimeWork">
						<tr>
							<!--A2_14 年月ラベル-->
							<td
								data-bind="text: yearMonth, style: { 'background-color': backgroundColor, 'color': textColor }"></td>
							<!--A2_15  限度ラベル-->
							<td
								data-bind="text: limitTime, style: { 'background-color': backgroundColor, 'color': textColor }"></td>
							<!--A2_16 実績ラベル-->
							<td
								data-bind="text: actualTime, style: { 'background-color': backgroundColor, 'color': textColor }"></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
    `
    @component({
        name: 'kaf010-share-header',
        template: template
    })
    class KAF010ShrHeaderModel extends ko.ViewModel {

        overTimeWork: KnockoutObservableArray<OvertimeWork>;
		/* ko.observableArray([
            new OvertimeWork(
				"2020/10", 
				10, 
				10, 
				10, 
				10, 
				"", 
				""),
             new OvertimeWork(
				"2020/11", 
				10, 
				10, 
				10, 
				10, 
				"", 
				""),    
        ]);
		*/
		created(params: any) {
			const self = this;
			self.overTimeWork = params.overTimeWork;
			self.overTimeWork.subscribe(value => {
				if (value) {
					console.log(value);
				}
			});
		}
		
		
		mounted() {
			
		}
    }

    export class OvertimeWork {
        yearMonth: KnockoutObservable<string> = ko.observable('');
        limitTime: KnockoutObservable<number> = ko.observable(0);
        actualTime: KnockoutObservable<number> = ko.observable(0);
        appTime: KnockoutObservable<number> = ko.observable(0);
        totalTime: KnockoutObservable<number> = ko.observable(0);
        backgroundColor: KnockoutObservable<string> = ko.observable('');
        textColor: KnockoutObservable<string> = ko.observable('');
        constructor() {
            
        }
    }
}