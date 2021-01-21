/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const useGuideTemplate = `
	<button class="ksu-popup-toggle btn-show-use-guide" data-bind="icon: 139"></button>
	<div class="ksu-popup bg-yellow use-guide" data-bind="foreach: models">
		<div class="group">
			<div class="group-title" data-bind="text: $i18n(title)"></div>
			<hr />
			<table>
				<tbody data-bind="foreach: _.chunk(colors, 3)">
					<tr data-bind="foreach: $data">
						<td class="guide">
							<div class="row">
								<div class="color" data-bind="style: { 'background-color': color }"></div>
								<div class="title" data-bind="text: $i18n(title)"></div>
							</div>							
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
`;

interface Color {
	color: string;
	title: string;
}

interface GroupColor {
	title: string;
	colors: Color[];
}

@component({
	name: 'ksu-use-guide',
	template: useGuideTemplate
})
class KSU001AUseGuideComponent extends ko.ViewModel {

	public models: GroupColor[] = [{
		title: 'KSU001_306',
		colors: [{
			color: '#ddddd2',
			title: 'KSU001_309'
		}, {
			color: '#00ffff',
			title: 'KSU001_310'
		}, {
			color: '#99ccff',
			title: 'KSU001_311'
		}, {
			color: '#a2dba8',
			title: 'KSU001_312'
		}, {
			color: '#eccefb',
			title: 'KSU001_313'
		}, {
			color: '#fedfe6',
			title: 'KSU001_314'
		}]
	}, {
		title: 'KSU001_307',
		colors: [{
			color: '#0000ff',
			title: 'KSU001_315'
		}, {
			color: '#ff0000',
			title: 'KSU001_316'
		}, {
			color: '#ffc000',
			title: 'KSU001_317'
		}, {
			color: '#198c17',
			title: 'KSU001_318'
		}]
	}, {
		title: 'KSU001_308',
		colors: [{
			color: '#8bd8ff',
			title: 'KSU001_319'
		}, {
			color: '#fabf8f',
			title: 'KSU001_320'
		}, {
			color: '#ffc0cb',
			title: 'KSU001_321'
		}, {
			color: '#ffff00',
			title: 'KSU001_322'
		}]
	}]; 
	
	created() {
	}

	mounted() {
	}
}