/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const settingMenuTemplate = `
	<button class="ksu-popup-toggle" data-bind="text: $i18n('KSU001_12')"></button>
	<div class="ksu-popup bg-yellow setting-menu" data-bind="foreach: groups">
		<div class="row-group">
			<div data-bind="ntsFormLabel: { text: $i18n(label) }"></div>
			<hr />
			<div class="group" data-bind="foreach: buttons">			
				<div class="row">
					<button data-bind="text: $i18n(btnText)" auto-close="true"></button>
					<div class="text">
						<span data-bind="text: $i18n(lblText)"></span>
					</div>
				</div>
			</div>
		</div>
	</div>
`;

@component({
	name: 'ksu-setting-menu',
	template: settingMenuTemplate
})
class KSU001ASettingMenuComponent extends ko.ViewModel {
	groups: GroupButton[] = [{
		label: 'KSU001_23',
		buttons: [{
			btnText: 'KSU001_24',
			lblText: 'KSU001_25'
		}, {
			btnText: 'KSU001_129',
			lblText: 'KSU001_130'
		}, {
			btnText: 'KSU001_131',
			lblText: 'KSU001_132'
		}]
	}, {
		label: 'KSU001_26',
		buttons: [{
			btnText: 'KSU001_27',
			lblText: 'KSU001_28'
		}]
	}, {
		label: 'KSU001_30',
		buttons: [{
			btnText: 'KSU001_31',
			lblText: 'KSU001_32'
		}, {
			btnText: 'KSU001_36',
			lblText: 'KSU001_33'
		}, {
			btnText: 'KSU001_34',
			lblText: 'KSU001_35'
		}]
	}];

	created() {
	}
}

interface Button {
	btnText: string;
	lblText: string;
}

interface GroupButton {
	label: string;
	buttons: Button[];
}