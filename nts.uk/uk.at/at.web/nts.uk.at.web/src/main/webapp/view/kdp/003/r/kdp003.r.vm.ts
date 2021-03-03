/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.r {

    @bean()
	export class ViewModel extends ko.ViewModel {

        beforeStopNoticeList: KnockoutObservableArray<any> = ko.observableArray([]);
            
        headOfficeNoticeList: KnockoutObservableArray<any> = ko.observableArray([]);

        workplaceNoticeList: KnockoutObservableArray<any> = ko.observableArray([]);

		stoppingNotice: KnockoutObservable<string> = ko.observable('');
        stopColor: KnockoutObservable<string> = ko.observable('');
        
        created() {
            const vm = this;
            vm.beforeStopNoticeList = ko.observableArray([{notice: '本日１０：００よりをメンテナンスのためシステムを停止致します。'}]);
            
			vm.headOfficeNoticeList = ko.observableArray([{notice: '全職場に表示されるメッセージですテストのメッセージ2行目'}]);
            
			vm.workplaceNoticeList = ko.observableArray([{notice: '職場管理者からのお願いです。今週土曜日は本社にてメンテナンスによる停電があります。'},
            {notice: '１２３４５６７８９①１２３４５６７８９②１２３４５６７８９③１２３４５６７８９④１２３４５６７８９⑤１２３４５６７８９⑥１２３４５６７８９⑦１２３４５６７８９⑧１２３４５６７８９⑨１２３４５６７８９⑩。'},
			{notice: '１２３４５６７８９①１２３４５６７８９②１２３４５６７８９③１２３４５６７８９④１２３４５６７８９⑤１２３４５６７８９⑥１２３４５６７８９⑦１２３４５６７８９⑧１２３４５６７８９⑨１２３４５６７８９⑩。'}]);
       		
			vm.stoppingNotice = ko.observable('現在システムはメンテナンスの為、停止されいています。メンテナンス終了予定は１５：００となります。');
			vm.stopColor('stopColor');
			
 }

        mounted() {
        }

        closeDialog() {
			const vm = this;
			vm.$window.close();
		}
    }

    export interface MessageNotice {
        notice: string;
        period: string;
    }

}