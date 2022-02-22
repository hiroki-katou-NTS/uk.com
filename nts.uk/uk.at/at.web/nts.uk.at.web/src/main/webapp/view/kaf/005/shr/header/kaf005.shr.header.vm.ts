module nts.uk.at.view.kaf005.shr.header.viewmodel {
	const template = `
<div class="right-panel-block" data-bind="with: $parent, visible: $parent.visibleModel.c6()">
	<!--A2_7 時間外労働ラベル-->
	<div class="header" data-bind="text: '時間外労働'"></div>
	<div class="content" data-bind="if: visibleModel.c6()">
		<div class="cf valign-center">
			<div class="ui-iggrid">
				<table id="kaf005_overtimeAgreement_table">
					<colgroup>
						<col width="100px" />
						<col width="100px" />
						<col width="100px" />
						<!--<col width="70px" />-->
						<!--<col width="70px" />-->
					</colgroup>
					<thead>
						<tr>
							<!--A2_9 年月ラベル-->
							<th class="kaf005_overtimeAgreement_header"
								data-bind="text: $i18n('KAF005_24')"></th>
							<!--A2_10  限度ラベル-->
							<th class="kaf005_overtimeAgreement_header"
								data-bind="text: '36上限時間'"></th>
							<!--A2_11 実績ラベル-->
							<th class="kaf005_overtimeAgreement_header"
								data-bind="text: $i18n('KAF005_26')"></th>
							<!--A2_12  申請ラベル-->
<!--							<th class="kaf005_overtimeAgreement_header" style="display: none"-->
<!--								data-bind="text: $i18n('KAF005_27')"></th>-->
							<!--A2_13  合計ラベル-->
<!--							<th class="kaf005_overtimeAgreement_header" style="display: none"-->
<!--								data-bind="text: $i18n('KAF005_28')"></th>-->
						</tr>
					</thead>
					<tbody data-bind="foreach: overTimeWork">
						<tr>
							<!--A2_14 年月ラベル-->
							<td
								data-bind="text: yearMonth, attr: { class: backgroundColor() + ' ' + textColor() } "></td>
							<!--A2_15  限度ラベル-->
							<td
								data-bind="text: $parent.getFormatTime(limitTime()), attr: { class: backgroundColor() + ' ' + textColor() }"></td>
							<!--A2_16 実績ラベル-->
							<td
								data-bind="text: $parent.getFormatTime(actualTime()), attr: { class: backgroundColor() + ' ' + textColor() }"></td>
							<!--A2_17  申請ラベル-->
<!--							<td style="display: none"-->
<!--								data-bind="text: appTime, attr: { class: backgroundColor() + ' ' + textColor() }"></td>-->
							<!--A2_18  合計ラベル-->
<!--							<td style="display: none"-->
<!--								data-bind="text: totalTime, attr: { class: backgroundColor() + ' ' + textColor() }"></td>-->
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
	`
	@component({
        name: 'kaf005-share-header',
		template: template
    })
	class KAF005ShrHeaderModel extends ko.ViewModel {
		
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
			/*
			constructor(yearMonth: string, limitTime: number, actualTime: number, appTime: number,  
                totalTime: number, backgroundColor: string, textColor: string) {
                this.yearMonth = ko.observable(yearMonth);
                this.limitTime = ko.observable(limitTime);
                this.actualTime = ko.observable(actualTime);
                this.appTime = ko.observable(appTime);
                this.totalTime = ko.observable(totalTime);
                this.backgroundColor = ko.observable(backgroundColor);
                this.textColor = ko.observable(textColor);
            }
			 */
            
        }	
}