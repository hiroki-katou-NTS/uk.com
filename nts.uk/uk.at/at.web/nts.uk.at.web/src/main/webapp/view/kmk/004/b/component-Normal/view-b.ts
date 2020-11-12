/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {
	const template = `
	<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Company'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="i18n: 'KMK004_225', click: add"></button>
		<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
	</div>
	<div class="view-b">
		<div class="header-b">
			<div data-bind="i18n: 'KMK004_228'"></div>
			<hr></hr>
			<div class="header_title">
				<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_229'"></div>
				<button data-bind="i18n: 'KMK004_231'"></button>
			</div>
			<div class="header_content">
				<div data-bind="component: {
					name: 'basic-setting',
					params:{
						modeCheckChangeSetting: modeCheckChangeSetting
					}
				}"></div>
			</div>
			<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_232'"></div>
		</div>
		<div class="content">
			<div>
				<button data-bind="i18n: 'KMK004_233'"></button>
			</div>
			<div class= "data">
				<div class= "box-year" data-bind="component: {
					name: 'box-year',
					params:{
					}
				}"></div>
				<div class= "time-work" data-bind="component: {
					name: 'time-work',
					params:{
					}
				}"></div>
			</div>
		</div>
	</div>
	<style type="text/css" rel="stylesheet">
		.view-b {
			padding: 10px;
		}
		
		.view-b .header-b {
			width: 435px;
		}
		
		.view-b .header-b ~ div {
			font-size: 20px;
		}
		
		.view-b .header-b hr {
			margin-top: 4px;
		}
		
		.view-b .header-b .header_title {
			margin-top: 15px;
		}
		
		.view-b .header-b .header_content {
			margin-top: 15px;
			margin-bottom: 15px;
		}
		
		.view-b .content {
			text-align: left;
			margin-top: 15px;
		}
		
		.view-b .content ~ div {
			margin-top: 15px;
			float: left;
		}

		.view-b .content .data {
			margin-top: 20px;
			float: left;
			display: flex;
		}

		.view-b .content .data .box-year {
			display: inline-block;
		}

		.view-b .content .data .time-work {
			display: inline-block;
			margin-left: 100px;
		}
    </style>
    <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
	`;

	interface Params {

	}

	@component({
		name: 'view-b',
		template
    })
    
	export class ViewBComponent extends ko.ViewModel {
		
		public modeCheckChangeSetting: KnockoutObservable<string> = ko.observable('');

		created(params: Params) {

		}

		mounted() {
			
		}

		add() {
			const vm = this;
			vm.modeCheckChangeSetting.valueHasMutated();
		}
    }
}