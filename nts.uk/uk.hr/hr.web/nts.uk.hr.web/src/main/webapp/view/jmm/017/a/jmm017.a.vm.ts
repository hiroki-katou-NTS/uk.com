module nts.uk.hr.view.jmm017.a.viewmodel {
    import viewModelTabB = nts.uk.hr.view.jmm017.b.viewmodel;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;

    export class ScreenModel {

        tab2ViewModel: KnockoutObservable<any>;
        
        usageFlgCommon: KnockoutObservable<boolean>;
        guideMsgAreaRow: KnockoutObservable<number>;
        guideMsgMaxNum: KnockoutObservable<number>;

        constructor() {
            let self = this;
          
            self.usageFlgCommon = ko.observable(false);
            self.guideMsgAreaRow = ko.observable(0);
            self.guideMsgMaxNum = ko.observable(0);
            nts.uk.ui.guide.operateCurrent('guidance/guideOperate', {screenGuideParam :[{programId:'JMM017',screenId:'A'},{programId:'JMM017',screenId:'B'}]}, 
                (programId, screenId) => {
                    if (programId === "JMM017" && screenId === "A") {
                        return "tabpanel-1";
                    } else if (programId === "JMM017" && screenId === "B") {
                        return "tabpanel-2";
                    }
                     
                   let contentArea = $(".sidebar-html")[0].getBoundingClientRect().height - ($("#header")[0].getBoundingClientRect().height + $(".sidebar-content-header")[0].getBoundingClientRect().height);
                    $(".wrapScroll").css({overflow: 'auto', height:contentArea +"px"}) ;
                }, Page.SIDEBAR);
              self.tab2ViewModel = ko.observable(new viewModelTabB.ScreenModel());
            //nts.uk.ui.guide.operate("hr", 'guidance/guideOperate', Page.SIDEBAR, { tab1: "", tab2: "" });
        }

        public startPage(): JQueryPromise<any> {
            block.grayout();
            var dfd = $.Deferred<any>();
            let self = this;
            $.when(self.StartPageBasicSetting(), self.tab2ViewModel().startPage()).done(function() {
                dfd.resolve(self);
                block.clear();
                $('#saveBtn').focus();
            });
            return dfd.promise();
        }

        StartPageBasicSetting(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            new service.getGuidance().done(function(data: any) {
                self.usageFlgCommon(data.usageFlgCommon);
                self.guideMsgAreaRow(data.guideMsgAreaRow);
                self.guideMsgMaxNum(data.guideMsgMaxNum);
            }).fail(function(errorInfor) {
                error({ messageId: errorInfor.messageId });
            }).always(function() {
                dfd.resolve();
            });

            return dfd.promise();
        }

        public onSelectTabA(): void {
            $(".nts-input").ntsError("clear");
            $("#sidebar").ntsSideBar("init", {
                active: SideBarTabIndex.FIRST,
                activate: (event, info) => {
                    $('#saveBtn').focus();
                    let self = this;
//                    self.StartPageBasicSetting();
                }
            });
        }

        public onSelectTabB(): void {
            $(".nts-input").ntsError("clear");
            $("#sidebar").ntsSideBar("init", {
                active: SideBarTabIndex.SECOND,
                activate: (event, info) => {
                    $('#combo-box1').focus();
                    let self = this;
//                    self.startPage();
                }
            });
        }
        
        public saveGuideDispSetting(): void {
            $('.nts-input').trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.grayout();
                let self = this;
                let param = {
                    usageFlgCommon: self.usageFlgCommon(),
                    guideMsgAreaRow: self.guideMsgAreaRow(),
                    guideMsgMaxNum: self.guideMsgMaxNum()
                }
                new service.saveGuideDispSetting(param).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(function(errorInfor) {
                    error({ messageId: errorInfor.messageId });
                }).always(function() {
                   block.clear();
                });
            }
        }
    }

    module SideBarTabIndex {
        export const FIRST = 0;
        export const SECOND = 1;
    }
    enum Page {
         NORMAL = 0,
         SIDEBAR = 1,
         FREE_LAYOUT = 2
    }

}