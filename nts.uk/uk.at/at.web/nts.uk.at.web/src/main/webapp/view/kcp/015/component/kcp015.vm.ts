/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
import setShared = nts.uk.ui.windows.setShared;

module nts.uk.ui.at.kcp015.shared {

    export interface Parameters {
        hasParams : KnockoutObservable<boolean>;
        visibleA31: KnockoutObservable<boolean>;
        visibleA32: KnockoutObservable<boolean>;
        visibleA33: KnockoutObservable<boolean>;
        visibleA34: KnockoutObservable<boolean>;
        visibleA35: KnockoutObservable<boolean>;
        visibleA36: KnockoutObservable<boolean>;
        sids      : KnockoutObservableArray<any>
        baseDate  : KnockoutObservable<string>;
    }

    const COMPONENT_NAME = 'kcp015-component';

    @handler({
        bindingName: COMPONENT_NAME
    })
    export class KCP015ComponentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;

            const selected = valueAccessor();
            const visibleA31 = allBindingsAccessor.get('visibleA31');
            const visibleA32 = allBindingsAccessor.get('visibleA32');
            const visibleA33 = allBindingsAccessor.get('visibleA33');
            const visibleA34 = allBindingsAccessor.get('visibleA34');
            const visibleA35 = allBindingsAccessor.get('visibleA35');
            const visibleA36 = allBindingsAccessor.get('visibleA36');
            const sids = allBindingsAccessor.get('sids');
            const baseDate = allBindingsAccessor.get('baseDate');

            const params = { visibleA31, visibleA32, visibleA33, visibleA34, visibleA35, visibleA36, sids, baseDate };
            const component = { name, params };

            ko.applyBindingsToNode(element, { component }, bindingContext);

            return { controlsDescendantBindings: true };
        }
    }

    @component({
        name: COMPONENT_NAME,
        template: `<!-- ko let: {text: nts.uk.resource.getText } -->
             <button tabindex="12" id="showPopup" data-bind="text: text('KCP015_1'), visible: visibleA1 "></button>
             <div id="A1" class="popup-area popup-panel btn10">
                <div id="button-top">
                    <button tabindex="1" class="small compensation" data-bind="text: text('Com_CompensationHoliday'), click: openKDL005, visible: visibleA31Com "></button>
                    <button tabindex="3" class="small paid"         data-bind="text: text('Com_PaidHoliday'),         click: openKDL020, visible: visibleA33Com "></button>
                    <button tabindex="5" class="small exsess"       data-bind="text: text('Com_ExsessHoliday'),       click: openKDL017, visible: visibleA35Com "></button>
                </div>
                <div id="button-bot">
                    <button tabindex="2" class="small substitute"     data-bind="text: text('Com_SubstituteHoliday'), click: openKDL009, visible: visibleA32Com "></button>
                    <button tabindex="4" class="small fundedPaid"     data-bind="text: text('Com_FundedPaidHoliday'), click: openKDL029, visible: visibleA34Com "></button>
                    <button tabindex="6" class="small supportsetting" data-bind="text: text('KCP015_2'),          click: openKDL039, visible: visibleA36Com "></button>
                </div>
             </div><!-- /ko -->`
    })
    export class ViewModel extends ko.ViewModel {
        
        visibleA1: KnockoutObservable<boolean> = ko.observable(true);
        visibleA31Com: KnockoutObservable<boolean> = ko.observable(true);
        visibleA32Com: KnockoutObservable<boolean> = ko.observable(true);
        visibleA33Com: KnockoutObservable<boolean> = ko.observable(true);
        visibleA34Com: KnockoutObservable<boolean> = ko.observable(true);
        visibleA35Com: KnockoutObservable<boolean> = ko.observable(true);
        visibleA36Com: KnockoutObservable<boolean> = ko.observable(true);

        constructor(private data: Parameters) {
            super();

            let vm = this;

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
            
            const { visibleA31, visibleA32, visibleA33, visibleA34, visibleA35, visibleA36, sids, baseDate } = vm.data;
            
            vm.getSetting();
        }

        created() {
            const vm = this;
            const { data } = vm;
        }

        public getSetting(): JQueryPromise<void> {
            let vm = this;
            let dfd = $.Deferred<void>();
            nts.uk.ui.block.grayout();
            nts.uk.request.ajax("at", "screen/at/kcp015/get").done((data: IData) => {
                
                vm.visibleA31Com(data.subLeaveUseDivision == null ? true : data.subLeaveUseDivision);

                vm.visibleA32Com(data.dvisionOfZhenxiuUse == null ? true : data.dvisionOfZhenxiuUse);

                vm.visibleA33Com(data.clsOfAnnualHoliday == null ? true : data.clsOfAnnualHoliday);

                vm.visibleA34Com(data.divisionOfAnnualHoliday == null ? true : data.divisionOfAnnualHoliday);

                vm.visibleA35Com(data.overtimeUseCls60H == null ? true : data.overtimeUseCls60H);
                
                vm.visibleA36Com(true);
                
                if (!vm.visibleA31Com() && !vm.visibleA33Com() && !vm.visibleA35Com()) {
                    $('#button-bot').css("margin-top", "0px");
                }

                if (!vm.visibleA32Com() && !vm.visibleA34Com() && !vm.visibleA36Com()) {
                    $('#button-bot').css("margin-top", "0px");
                }
                
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            }).always((data) => {
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }

        // A1_10_2
        openKDL005() {
            let vm = this;

            let empIds = vm.data.sids();
            let baseDate = moment().format('YYYYMMDD');
            let param: any = {
                employeeIds: empIds,
                baseDate: baseDate
            };

            nts.uk.ui.windows.setShared('KDL005_DATA', param);
            $('#A1_10_1').ntsPopup('hide');
            if (param.employeeIds.length > 1) {
                nts.uk.ui.windows.sub.modal("/view/kdl/005/a/multi.xhtml");
            } else {
                nts.uk.ui.windows.sub.modal("/view/kdl/005/a/single.xhtml");
            }
        }

        // A1_10_3
        openKDL009() {
            let vm = this;

            let empIds = vm.data.sids();
            let baseDate = moment().format('YYYYMMDD');
            var param: any = {
                employeeIds: empIds,
                baseDate: baseDate
            };

            nts.uk.ui.windows.setShared('KDL009_DATA', param);
            $('#A1_10_1').ntsPopup('hide');
            if (param.employeeIds.length > 1) {
                nts.uk.ui.windows.sub.modal("/view/kdl/009/a/multi.xhtml");
            } else {
                nts.uk.ui.windows.sub.modal("/view/kdl/009/a/single.xhtml");
            }
        }

        // A1_10_4
        openKDL020() {
            let vm = this;
            var param: any = {
                employeeIds: vm.data.sids(),
                baseDate: new Date()
            };
            setShared('KDL020A_PARAM', param );
            $('#A1_10_1').ntsPopup('hide');
            if (param.employeeIds.length == 1) {
                nts.uk.ui.windows.sub.modal('/view/kdl/020/a/single.xhtml').onClosed(function(): any { });
            } else {
                nts.uk.ui.windows.sub.modal('/view/kdl/020/a/multi.xhtml').onClosed(function(): any { });
            }
        }

        // A1_10_5
        openKDL029() {
            let vm = this;
            let param = {
                employeeIds: vm.data.sids(),
                baseDate: moment(new Date()).format("YYYY/MM/DD")
            }
            setShared('KDL029_PARAM', param);
            $('#A1_10_1').ntsPopup('hide');
            nts.uk.ui.windows.sub.modal('/view/kdl/029/a/index.xhtml').onClosed(function(): any {
            });
        }
        
        openKDL017() {
            let vm = this;
            let data = {employeeIds: vm.data.sids(), baseDate: moment(new Date()).format("YYYYMMDD")}
            setShared('KDL017_PARAM', data);
            if(data.employeeIds.length > 1) {
                nts.uk.ui.windows.sub.modal("/view/kdl/017/a/multiple.xhtml");
            } else {
                nts.uk.ui.windows.sub.modal("/view/kdl/017/a/single.xhtml");
            }
        }

        openKDL039() {
            let vm = this;
            let sids = vm.data.sids();
            let baseDate = vm.data.baseDate();
            // to do
        }
    }

    interface IData {
        clsOfAnnualHoliday: boolean;     // 年休の使用区分
        divisionOfAnnualHoliday: boolean;// 積立年休使用区分
        overtimeUseCls60H: boolean;      // 60H超休使用区分
        dvisionOfZhenxiuUse: boolean;    // 振休使用区分
        subLeaveUseDivision: boolean;    // 代休使用区分
    }
}