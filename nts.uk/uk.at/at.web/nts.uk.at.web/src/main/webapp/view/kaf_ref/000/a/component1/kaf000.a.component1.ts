module nts.uk.at.view.kaf000_ref.a.component1.viewmodel {

    @component({
        name: 'kaf000-a-component1',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component1/index.html'
    })
    class Kaf000AComponent1ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        message: KnockoutObservable<string>; 
        deadline: KnockoutObservable<string>;
        displayArea: KnockoutObservable<boolean>; 
        displayMsg: KnockoutObservable<boolean>;
        displayDeadline: KnockoutObservable<boolean>;
        
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.message = ko.observable("line111111111111111111");
            vm.deadline = ko.observable("line222222222222222222");
            vm.displayArea = ko.pureComputed(() => {
                return vm.displayMsg() && vm.displayDeadline();
            });
            vm.displayMsg = ko.observable(false);
            vm.displayDeadline = ko.observable(false);
            
            vm.appDispInfoStartupOutput.subscribe(value => {
                vm.message(value.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0].memo);
                if(_.isEmpty(vm.message())) {
                    vm.displayMsg(false);         
                } else {
                    vm.displayMsg(true);
                }
                
                let advanceAppAcceptanceLimit = value.appDispInfoNoDateOutput.advanceAppAcceptanceLimit == 1;
                let allowFutureDay = value.appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting.afterhandRestriction.allowFutureDay;
                let appDeadlineUseCategory = value.appDispInfoWithDateOutput.appDeadlineUseCategory == 1;
                // 注意：申請表示情報(基準日関係なし)．事前申請の受付制限が利用しない && 申請表示情報．申請設定（基準日関係なし）．申請承認設定．申請設定．受付制限設定．事後の受付制限．未来日許可しないがfalse && 申請表示情報(基準日関係あり)．申請締め切り日利用区分が利用しない
                if(!advanceAppAcceptanceLimit && !allowFutureDay && !appDeadlineUseCategory) {
                    // 締め切りエリア全体に非表示
                    vm.displayDeadline(false);        
                } else {
                    vm.displayDeadline(true);     
                }
                // {1}事前受付日
                let prePart = "";
                if(advanceAppAcceptanceLimit) {
                    // ・申請表示情報(基準日関係なし)．事前受付時分がNull
                    if(_.isNull(value.appDispInfoNoDateOutput.opAdvanceReceptionHours)) {
                        prePart = vm.$i18n('KAF000_38', [value.appDispInfoNoDateOutput.opAdvanceReceptionDate]);        
                    } 
                    // ・申請表示情報(基準日関係なし)．事前受付時分がNullじゃない
                    else {
                        prePart = vm.$i18n('KAF000_41', [value.appDispInfoNoDateOutput.opAdvanceReceptionHours]);  
                    }             
                }
                // {2}事後受付日
                let postPart = vm.$i18n('KAF000_39', [moment(vm.$date.today()).format("YYYY/MM/DD")]);
                // {3}締め切り期限日
                let deadlinePart = vm.$i18n('KAF000_40', [value.appDispInfoWithDateOutput.opAppDeadline]);
                vm.deadline(prePart + postPart + deadlinePart);
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}