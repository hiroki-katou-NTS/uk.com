/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

const mocktabs = [
	{
		"pageNo": 1,
		"stampPageName": "f",
		"stampPageComment": "",
		"stampPageCommentColor": "#000000",
		"buttonLayoutType": 0,
		"buttonSettings": [
			{
				"btnPositionNo": 1,
				"btnName": "出勤",
				"btnTextColor": "#0033cc",
				"btnBackGroundColor": "#ccccff",
				"btnReservationArt": 0,
				"changeHalfDay": false,
				"goOutArt": null,
				"setPreClockArt": 0,
				"changeClockArt": 0,
				"changeCalArt": 0,
				"usrArt": 1,
				"audioType": 0,
				"btnDisplayType": 1
			},
			{
				"btnPositionNo": 2,
				"btnName": "直行",
				"btnTextColor": "#0033cc",
				"btnBackGroundColor": "#ccccff",
				"btnReservationArt": 0,
				"changeHalfDay": false,
				"goOutArt": 0,
				"setPreClockArt": 0,
				"changeClockArt": 1,
				"changeCalArt": 2,
				"usrArt": 1,
				"audioType": 0,
				"btnDisplayType": 2
			},
			{
				"btnPositionNo": 3,
				"btnName": "早出",
				"btnTextColor": "#0033cc",
				"btnBackGroundColor": "#ccccff",
				"btnReservationArt": 0,
				"changeHalfDay": false,
				"goOutArt": 0,
				"setPreClockArt": 0,
				"changeClockArt": 4,
				"changeCalArt": 0,
				"usrArt": 1,
				"audioType": 0,
				"btnDisplayType": 3
			},
			{
				"btnPositionNo": 4,
				"btnName": "戻り",
				"btnTextColor": "#0033cc",
				"btnBackGroundColor": "#ccccff",
				"btnReservationArt": 0,
				"changeHalfDay": false,
				"goOutArt": 0,
				"setPreClockArt": 0,
				"changeClockArt": 5,
				"changeCalArt": 0,
				"usrArt": 1,
				"audioType": 0,
				"btnDisplayType": 4
			}
		]
	},
	{
		"pageNo": 2,
		"stampPageName": "Page",
		"stampPageComment": "DKM",
		"stampPageCommentColor": "#000000",
		"buttonLayoutType": 1,
		"buttonSettings": [
			{
				"btnPositionNo": 1,
				"btnName": "出勤",
				"btnTextColor": "#0033cc",
				"btnBackGroundColor": "#ccccff",
				"btnReservationArt": 0,
				"changeHalfDay": false,
				"goOutArt": null,
				"setPreClockArt": 0,
				"changeClockArt": 0,
				"changeCalArt": 0,
				"usrArt": 1,
				"audioType": 0,
				"btnDisplayType": 1
			},
			{
				"btnPositionNo": 2,
				"btnName": "直行",
				"btnTextColor": "#0033cc",
				"btnBackGroundColor": "#ccccff",
				"btnReservationArt": 0,
				"changeHalfDay": false,
				"goOutArt": 0,
				"setPreClockArt": 0,
				"changeClockArt": 1,
				"changeCalArt": 2,
				"usrArt": 1,
				"audioType": 0,
				"btnDisplayType": 2
			},
			{
				"btnPositionNo": 3,
				"btnName": "早出",
				"btnTextColor": "#0033cc",
				"btnBackGroundColor": "#ccccff",
				"btnReservationArt": 0,
				"changeHalfDay": false,
				"goOutArt": 0,
				"setPreClockArt": 0,
				"changeClockArt": 4,
				"changeCalArt": 0,
				"usrArt": 1,
				"audioType": 0,
				"btnDisplayType": 3
			},
			{
				"btnPositionNo": 4,
				"btnName": "戻り",
				"btnTextColor": "#0033cc",
				"btnBackGroundColor": "#ccccff",
				"btnReservationArt": 0,
				"changeHalfDay": false,
				"goOutArt": 0,
				"setPreClockArt": 0,
				"changeClockArt": 5,
				"changeCalArt": 0,
				"usrArt": 1,
				"audioType": 0,
				"btnDisplayType": 4
			}
		]
	}
];

module nts.uk.at.kdp003.a {
	@bean()
	export class KDP003AViewModel extends ko.ViewModel {
		tabs = mocktabs;
		created() {
		}

		mounted() {
		}


		setting() {
			const vm = this;

			vm.$window.modal('/view/kdp/003/f/index.xhtml', { mode: 'admin' });

			console.log(vm);
		}

		company() {
			const vm = this;

			vm.playAudio();

			// vm.$window.modal('/view/kdp/003/f/index.xhtml', { mode: 'employee' });

			console.log(vm);
		}


		playAudio() {
			const vm = this;

			const audio: HTMLAudioElement = document.createElement('audio');
			const source: HTMLSourceElement = document.createElement('source');

			source.src = 'https://www.w3schools.com/TAGS/horse.ogg';
			audio.append(source);

			audio.play();
		}
		
		stampButtonClick(btn: any) {
			const vm = this;
			
			console.log(vm, btn);
		}
	}
}
