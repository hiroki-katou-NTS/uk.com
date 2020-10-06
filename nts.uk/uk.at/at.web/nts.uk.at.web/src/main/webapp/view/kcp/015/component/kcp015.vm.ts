import text = nts.uk.resource.getText;

ko.components.register('kcp015-component', {
    viewModel: {
        createViewModel: function(params) {
            var vm = new KCP015ScreenModel(params);
            return vm;
        }
    },
    template: `<!-- ko let: {text: nts.uk.resource.getText } -->
     <button id="showPopup" data-bind="text: text('KCP015_1'), visible: visibleA1 "></button>
     <div id="A1" class="popup-area popup-panel btn10">
        <div id="button-top">
            <button id="A3_1" class="small compensation" data-bind="text: text('Com_CompensationHoliday'), click: openKDL005, visible: visibleA31 "></button>
            <button id="A3_3" class="small paid" data-bind="text: text('Com_PaidHoliday'), click: openKDL005, visible: visibleA33 "></button>
            <button id="A3_5" class="small exsess"data-bind="text: text('Com_ExsessHoliday'), click: openKDL005, visible: visibleA32 "></button>
        </div>
        <div id="button-bot">
            <button id="A3_2" class="small substitute" data-bind="text: text('Com_SubstituteHoliday'), click: openKDL005, visible: visibleA32 "></button>
            <button id="A3_4" class="small fundedPaid" data-bind="text: text('Com_FundedPaidHoliday'), click: openKDL005, visible: visibleA34 "></button>
            <button id="A3_6" class="small supportsetting" data-bind="text: text('KCP015_2'), click: openKDL005, visible: visibleA34 "></button>
        </div>
     </div><!-- /ko -->`
});

class KCP015ScreenModel {

    visibleA1: KnockoutObservable<boolean> = ko.observable(true);
    visibleA31: KnockoutObservable<boolean> = ko.observable(true);
    visibleA32: KnockoutObservable<boolean> = ko.observable(true);
    visibleA33: KnockoutObservable<boolean> = ko.observable(true);
    visibleA34: KnockoutObservable<boolean> = ko.observable(true);
    visibleA35: KnockoutObservable<boolean> = ko.observable(true);
    visibleA36: KnockoutObservable<boolean> = ko.observable(true);
    constructor(param) {
        var self = this;

        if (param.haveData() == false) {
            console.log('no param');
            self.getSetting();
        } else {
            if (!param.visibleA31() && !param.visibleA32() && !param.visibleA33() && !param.visibleA34() && !param.visibleA35() && !param.visibleA36()) {
                $("#A1").addClass("hiddenBtn");
            }

            if (param.visibleA31()) {
                $("#A3_1").removeClass("hiddenBtn");
            } else {
                $("#A3_1").addClass("hiddenBtn");
            }

            if (param.visibleA32()) {
                $("#A3_2").removeClass("hiddenBtn");
            } else {
                $("#A3_2").addClass("hiddenBtn");
            }

            if (param.visibleA33()) {
                $("#A3_3").removeClass("hiddenBtn");
            } else {
                $("#A3_3").addClass("hiddenBtn");
            }

            if (param.visibleA34()) {
                $("#A3_4").removeClass("hiddenBtn");
            } else {
                $("#A3_4").addClass("hiddenBtn");
            }

            if (param.visibleA35()) {
                $("#A3_5").removeClass("hiddenBtn");
            } else {
                $("#A3_5").addClass("hiddenBtn");
            }

            if (param.visibleA36()) {
                $("#A3_6").removeClass("hiddenBtn");
            } else {
                $("#A3_6").addClass("hiddenBtn");
            }
        }
        
        $('#A1').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom+3',
                of: $('#showPopup')
            }
        });
        
        $('#showPopup').click(function() {
            $('#A1').ntsPopup("toggle");
        });

        //self.visibleA1(true);
        //self.visibleA31(false);
        //self.visibleA32(param.visibleA32());
        //self.visibleA33(param.visibleA33());
        //self.visibleA34(param.visibleA34());
        //self.visibleA35(param.visibleA35());
        //self.visibleA36(param.visibleA36());
    }
    
    public getSetting(): JQueryPromise<void> {
        let self = this;
        let dfd = $.Deferred<void>();
        nts.uk.ui.block.grayout();
        nts.uk.request.ajax("at", "screen/at/kcp015/get").done((data: IData) => {
            
            if (data.clsOfAnnualHoliday) {
                $("#A3_1").removeClass("hiddenBtn");
            } else {
                $("#A3_1").addClass("hiddenBtn");
            }

            if (data.divisionOfAnnualHoliday) {
                $("#A3_2").removeClass("hiddenBtn");
            } else {
                $("#A3_2").addClass("hiddenBtn");
            }

            if (data.overtimeUseCls60H) {
                $("#A3_3").removeClass("hiddenBtn");
            } else {
                $("#A3_3").addClass("hiddenBtn");
            }

            if (data.dvisionOfZhenxiuUse) {
                $("#A3_4").removeClass("hiddenBtn");
            } else {
                $("#A3_4").addClass("hiddenBtn");
            }

            if (data.subLeaveUseDivision) {
                $("#A3_5").removeClass("hiddenBtn");
            } else {
                $("#A3_5").addClass("hiddenBtn");
            }
            dfd.resolve();
        }).fail(function() {
            dfd.reject();
        }).always((data) => {
            nts.uk.ui.block.clear();
        });
        return dfd.promise();
    }

    openKDL005() {
        let self = this;
        //nts.uk.ui.windows.sub.modal("/view/kdl/005/a/multi.xhtml");
    }

    public startPage(): JQueryPromise<void> {
        let self = this;
        let dfd = $.Deferred<void>();
        dfd.resolve();
        return dfd.promise();
    }
}

interface IData {
    clsOfAnnualHoliday: boolean;     // 年休の使用区分
    divisionOfAnnualHoliday: boolean;// 積立年休使用区分
    overtimeUseCls60H: boolean;      // 60H超休使用区分
    dvisionOfZhenxiuUse: boolean;    // 振休使用区分
    subLeaveUseDivision: boolean;    // 代休使用区分
}