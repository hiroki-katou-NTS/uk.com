/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmp001.h {
	const template = `
		<div id="functions-area">
			<button data-bind= "text: $i18n('KMP001_107'), click: print"></button>
			<button data-bind= "text: $i18n('KMP001_7'), click: showDiaLog"></button>
		</div>
		<div class="view-kmp">
			<div class="panel panel-frame view-h-title">
				<pre data-bind= "text: $i18n('KMP001_101')"></pre>
			</div>
			<div class="list-panel">
				<table>
					<tbody>
						<tr>
							<td>
								<div class="panel panel-frame supper">
									<div class="panel panel-frame panel-gray-bg">
										<div class="title" data-bind= "text: $i18n('KMP001_1')"></div>
									</div>
									<div class="content" data-bind= "text: $i18n('KMP001_102')"></div>
									<div class="button-select">
										<span class="caret-right caret-inline">
											<button class="large" data-bind= "text: $i18n('KMP001_1'), click: view_A"></button>
										</span>
									</div>
								</div>
							</td>
							<td>
								<div class="panel panel-frame supper">
									<div class="panel panel-frame panel-gray-bg">
										<div class="title" data-bind= "text: $i18n('KMP001_2')"></div>
									</div>
									<div class="content" data-bind= "text: $i18n('KMP001_103')"></div>
									<div class="button-select">
										<span class="caret-right caret-inline">
											<button class="large" data-bind= "text: $i18n('KMP001_2'), click: view_B, enable: attendance"></button>
										</span>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="panel panel-frame supper">
									<div class="panel panel-frame panel-gray-bg">
										<div class="title" data-bind= "text: $i18n('KMP001_3')"></div>
									</div>
									<div class="content" data-bind= "text: $i18n('KMP001_104')"></div>
									<div class="button-select">
										<span class="caret-right caret-inline">
											<button class="large" data-bind= "text: $i18n('KMP001_3'), click: view_C, enable: attendance"></button>
										</span>
									</div>
									</div>
							</td>
							<td>
								<div class="panel panel-frame supper">
									<div class="panel panel-frame panel-gray-bg">
										<div class="title" data-bind= "text: $i18n('KMP001_70')"></div>
									</div>
									<div class="content" data-bind= "text: $i18n('KMP001_105')"></div>
									<div class="button-select">
										<span class="caret-right caret-inline">
											<button class="large" data-bind= "text: $i18n('KMP001_70'), click: view_E, enable: attendance"></button>
										</span>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	`;

	interface Params {
		model: KnockoutObservable<string>;
	}

	const KMP001H_API = {
		GET_STAMPCARDDIGIT: 'screen/pointCardNumber/getStampCardDigit',
		PRINT: 'file/stampEditting/report/export'
	};

	@component({
		name: 'view-h',
		template
	})

	export class ViewCComponent extends ko.ViewModel {

		public params!: Params;
		public attendance: KnockoutObservable<boolean> = ko.observable(true);
		public stampCardEdit: StampCardEdit = new StampCardEdit();

		created(params: Params) {
			const vm = this;
			vm.params = params;
			vm.getSetting();
			
			if (vm.$user.role.isInCharge.attendance) {
				vm.attendance(true);
			} else {
				vm.attendance(false);
			}
			
		}

		view_A() {
			const vm = this;
			vm.params.model('KMP001_A');
		}

		view_B() {
			const vm = this;
			vm.params.model('KMP001_B');
		}

		view_C() {
			const vm = this;
			vm.params.model('KMP001_C');
		}

		view_E() {
			const vm = this;
			vm.params.model('KMP001_E');
		}

		showDiaLog() {
			const vm = this;

			vm.$window
				.modal('/view/kmp/001/d/index.xhtml');
		}

		print() {
			const vm = this,
			param = {digitsNumber: ko.toJS(vm.stampCardEdit.stampCardDigitNumber), stampMethod: ko.toJS(vm.stampCardEdit.stampCardEditMethod)};
			
			nts.uk.request.exportFile('file/stampEditting/report/export', param);
		}

		getSetting() {
			const vm = this;

			vm.$ajax(KMP001H_API.GET_STAMPCARDDIGIT)
				.then((data: IStampCardEdit) => {
					vm.stampCardEdit.update(data);
				});
		}
	}
}