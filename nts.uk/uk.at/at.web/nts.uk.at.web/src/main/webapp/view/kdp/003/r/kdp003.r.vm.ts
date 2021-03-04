/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.r {

    @bean()
	export class ViewModel extends ko.ViewModel {
        // R5 利用停止前内容
        beforeStopNoticeList: KnockoutObservableArray<any> = ko.observableArray([]);
        
        // R1 本部見内容
        headOfficeNoticeList: KnockoutObservableArray<any> = ko.observableArray([]);

        // R2 職場メッセージ
        workplaceNoticeList: KnockoutObservableArray<any> = ko.observableArray([]);

        // R6 利用停止中内容
		stoppingNotice: KnockoutObservable<string> = ko.observable('');

        isNormalMode: KnockoutObservable<boolean> = ko.observable(true);

        parentParam = new ParentParam();

        created(parentParam: ParentParam) {
            const vm = this;

            vm.parentParam = parentParam;

			vm.headOfficeNoticeList = ko.observableArray([{notice: '全職場に表示されるメッセージですテストのメッセージ2行目'}]);
            
			vm.workplaceNoticeList = ko.observableArray([{notice: '職場管理者からのお願いです。今週土曜日は本社にてメンテナンスによる停電があります。'},
            {notice: '１２３４５６７８９①１２３４５６７８９②１２３４５６７８９③１２３４５６７８９④１２３４５６７８９⑤１２３４５６７８９⑥１２３４５６７８９⑦１２３４５６７８９⑧１２３４５６７８９⑨１２３４５６７８９⑩。'}]);
       		
 }

        mounted() {
        }

        closeDialog() {
			const vm = this;
			vm.$window.close();
		}
    }

    export class ParentParam {
		noticeSet: NoticeSet;
	}

    export class NoticeSet {
        comMsgColor: ColorSettingDto ; // 会社メッセージ色
        companyTitle: string; //会社宛タイトル
        wkpMsgColor: ColorSettingDto //職場メッセージ色
        wkpTitle: string //職場宛タイトル
        constructor(init?: Partial<NoticeSet>) {
			$.extend(this, init);
		}
    }

    export class ColorSettingDto {
		textColor: string; // 文字色
        backGroundColor: string // 背景色
		constructor(init?: Partial<ColorSettingDto>) {
			$.extend(this, init);
		}
	}



}