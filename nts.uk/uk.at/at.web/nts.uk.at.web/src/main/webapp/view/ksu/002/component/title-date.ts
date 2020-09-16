/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {

	const template = `
		<div class="title-date">
			<div class="backgroud" >
				<div class="content">
					<div data-bind="ntsDatePicker: { value: yearMonth, dateFormat: 'yearmonth' , valueFormat: 'YYYYMM',  fiscalMonthsMode: true, defaultClass: 'round-orange' }">
					</div>
					<span class="label-1" class="label" data-bind="i18n: 'KSU002_23'"></span>
					<span class="label-2" class="label" data-bind="i18n: 'KSU002_7'"></span>
					<div>Chung dep trai
					</div>
				</div>
			</div>
		</div>
		<style type="text/css" rel="stylesheet">
            .title-date .backgroud {
				border: 1px solid #cccccc;
				background: #edfac2;
				padding: 6px;
				margin: 10px;
				width: 500px;
			}
			
			.title-date .backgroud .content .label-1 {
				margin-left: 10px;
			}
			
			.title-date .backgroud .content .label-2 {
				margin-left: -6px;
			}
			
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	interface Params {

	}

	@component({
		name: 'title-date',
		template
	})
	export class TitleDateComponent extends ko.ViewModel {

		public yearMonth: KnockoutObservable<number> = ko.observable(-1);

		created(params: Params) {
			const vm = this;
			
		}

		mounted() {
		}
	}
}