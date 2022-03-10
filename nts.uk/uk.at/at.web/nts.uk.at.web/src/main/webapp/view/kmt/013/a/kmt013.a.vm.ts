module nts.uk.at.view.kmt013.a {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import Moment = moment.Moment;
    const PATH = {
        getAllSetWkps: "at/screen/kmt013/findAll",
        getById: 'at/screen/kmt013/findAllById',
        register: 'at/share/supportmanagement/supportalloworg/register',
        copy: 'at/share/supportmanagement/supportalloworg/copy',
        delete: 'at/share/supportmanagement/supportalloworg/delete',
    };

    @bean()
    class ViewModel extends ko.ViewModel {

        unit: KnockoutObservable<number> = ko.observable(0); // WORKPLACE = 0, WORKPLACE GROUP = 1
        multiple: KnockoutObservable<boolean>;
        onDialog: KnockoutObservable<boolean>;
        showAlreadySetting: KnockoutObservable<boolean> = ko.observable(true);
        selectType: KnockoutObservable<number>;
        rows: KnockoutObservable<number>;
        baseDate: KnockoutObservable<any>;
        alreadySettingWorkplaces: KnockoutObservableArray<{workplaceId: string, isAlreadySetting: boolean}>;
        alreadySettingWorkplaceGroups: KnockoutObservableArray<string>;
        selectedWkpId: KnockoutObservable<any>;
        selectedWkpGroupId: KnockoutObservable<any>;

        isWorkplaceGroupMode: KnockoutObservable<boolean>;
        selectedWkp: KnockoutObservable<any>;
        selectedWkpGroup: KnockoutObservable<any>;

        supportableList: KnockoutObservableArray<SupportableList>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        count: number = 100;

        a3_1Txt: KnockoutObservable<string> = ko.observable(null);
        currentWrkName: KnockoutObservable<string> = ko.observable('');
        currentWrkCode: KnockoutObservable<string> = ko.observable('');
        a4_1Txt: KnockoutObservable<string> = ko.observable(null);

        updateMode: KnockoutObservable<boolean> = ko.observable(false);
        isA5Checked: KnockoutObservable<boolean>;
        isA2NotEmpty: KnockoutObservable<boolean>;
        enableA43Btn: KnockoutObservable<boolean>;
        componentName: KnockoutObservable<string> = ko.observable("kcp017-component");
        created() {
            const vm = this;

            vm.multiple = ko.observable(false);
            vm.onDialog = ko.observable(false);
            vm.selectType = ko.observable(3);
            vm.rows = ko.observable(11);
            vm.baseDate = ko.observable(new Date);
            vm.alreadySettingWorkplaces = ko.observableArray([]);
            vm.alreadySettingWorkplaceGroups = ko.observableArray([]);
            vm.selectedWkpId = ko.observable(null);
            vm.selectedWkpGroupId = ko.observable(null);

            vm.isWorkplaceGroupMode = ko.observable(false);
            vm.selectedWkp = ko.observable(null);
            vm.selectedWkpGroup = ko.observable(null);

            vm.supportableList = ko.observableArray([]);
            vm.currentCode = ko.observable();
            vm.currentCodeList = ko.observableArray([]);

            vm.a3_1Txt = ko.observable(vm.$i18n('Com_Workplace') + ': ');
            vm.a4_1Txt = ko.observable(vm.$i18n('応援可能') + vm.$i18n('Com_Workplace') + vm.$i18n('リスト'));

            vm.isA5Checked = ko.computed(() => {
                return vm.currentCodeList().length > 0;
            })

            vm.isA2NotEmpty = ko.observable(true);

            vm.enableA43Btn = ko.computed(() => {
                return vm.isA2NotEmpty() && vm.isA5Checked();
            })
            vm.unit.subscribe((value: number)=>{
                vm.componentName.valueHasMutated();
               vm.isWorkplaceGroupMode(value == OrgUnit.WORKPLACEGROUP);
                $("#A4_2").focus();
                if (vm.isWorkplaceGroupMode()) {
                    vm.a3_1Txt(vm.$i18n('Com_WorkplaceGroup') + ': ');
                    vm.a4_1Txt('応援可能' + vm.$i18n('Com_WorkplaceGroup') + 'リスト');
                    vm.rows(10);
                    vm.selectedWkpGroupId.valueHasMutated();
                } else {
                    vm.a3_1Txt(vm.$i18n('Com_Workplace') + ': ');
                    vm.a4_1Txt('応援可能' + vm.$i18n('Com_Workplace') + 'リスト');
                    vm.rows(11);
                    vm.selectedWkpId.valueHasMutated();
                }
            });
            vm.selectedWkpId.subscribe((newValue) => {
                if (!vm.isWorkplaceGroupMode()){
                    if (_.isEmpty(newValue)){
                        vm.isA2NotEmpty(false);
                    } else {
                        vm.isA2NotEmpty(true);
                    }
                }
                let param: TargetOrgParams = new TargetOrgParams(moment.utc(),vm.unit(),vm.unit() == OrgUnit.WORKPLACE ? vm.selectedWkpId(): vm.selectedWkpGroupId());
                vm.$ajax(PATH.getById,param).done((data: TargetOrgInfo) => {
                    if (!_.isEmpty(data.supportableOrgInfoDtoList)){
                        vm.updateMode(true);
                    } else {
                        vm.updateMode(false);
                    }
                    vm.currentWrkCode(data.code);
                    vm.currentWrkName(data.name);

                    vm.supportableList( _.sortBy(data.supportableOrgInfoDtoList.map(id => new SupportableList(id.orgId,id.code,id.name)),(s)=>{return s.code;}));
                }).fail(error => {
                    vm.$dialog.error(error);
                }).always(() => {
                    $("#A4_2").focus();
                    vm.$blockui("hide");
                });
            });

            vm.selectedWkpGroupId.subscribe((newValue) => {
                if (vm.isWorkplaceGroupMode()){
                    if (_.isEmpty(newValue)){
                        vm.isA2NotEmpty(false);
                    } else {
                        vm.isA2NotEmpty(true);
                    }
                }
                let param: TargetOrgParams = new TargetOrgParams(moment.utc(),vm.unit(),vm.unit() == OrgUnit.WORKPLACE ? vm.selectedWkpId(): vm.selectedWkpGroupId());
                vm.$ajax(PATH.getById,param).done((data: TargetOrgInfo) => {
                    if (!_.isEmpty(data.supportableOrgInfoDtoList)){
                        vm.updateMode(true);
                    } else {
                        vm.updateMode(false);
                    }
                    vm.currentWrkCode(data.code);
                    vm.currentWrkName(data.name);
                    vm.supportableList(_.sortBy(data.supportableOrgInfoDtoList.map(id => new SupportableList(id.orgId,id.code,id.name)),(s)=>{return s.code;}));
                }).fail(error => {
                    vm.$dialog.error(error);
                }).always(() => {
                    $("#A4_2").focus();
                    vm.$blockui("hide");
                });
            });

        }

        mounted() {
            const vm = this;
            $("#A4_2").focus();
            $('#kcp017-component').attr("tabindex", -1);
            vm.getAlreadySettingList();
        }

        registerSupport() {
            const vm = this;
            vm.$blockui("show").then(() => {
                const data: RegisterSupportAllowOrgCommand
                    = new RegisterSupportAllowOrgCommand(vm.unit(),vm.unit() == OrgUnit.WORKPLACE ? vm.selectedWkpId(): vm.selectedWkpGroupId(),_.map(vm.supportableList(),(item)=>{
                        return new OrgCanBeSupportDto(item.id);
                }));
                return vm.$ajax(PATH.register, data);
            }).done(() => {
                vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                    vm.getAlreadySettingList();
                    if (vm.isWorkplaceGroupMode()){
                        vm.selectedWkpGroupId.valueHasMutated();
                    } else{
                        vm.selectedWkpId.valueHasMutated();
                    }

                });
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                $('.A6_1').focus();
                vm.$blockui("hide");
            });
        }

        deleteSupport() {
            const vm = this;
            vm.$dialog.confirm({messageId: 'Msg_18'}).then((result) => {
                if (result === 'yes') {
                    vm.$blockui('show');
                    let command: DeleteSupportAllowOrgCommand = new DeleteSupportAllowOrgCommand(vm.unit(),vm.unit() == OrgUnit.WORKPLACE ? vm.selectedWkpId(): vm.selectedWkpGroupId());
                    vm.$ajax(PATH.delete, command).done(() => {
                        vm.$dialog.info({messageId: 'Msg_16'}).then(() => {
                            vm.getAlreadySettingList();
                            if (vm.isWorkplaceGroupMode()){
                                vm.selectedWkpGroupId.valueHasMutated();
                            } else{
                                vm.selectedWkpId.valueHasMutated();
                            }
                        });
                    }).fail((error) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        $('.A6_1').focus();
                        vm.$blockui('hide');
                    });
                }
            });
        }

        getAlreadySettingList() {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(PATH.getAllSetWkps).done((data: Array<SupportFuncGetOrganizationDto>) => {
                vm.alreadySettingWorkplaceGroups(_.map(_.filter(data,(item)=>{return item.unit == OrgUnit.WORKPLACEGROUP}),gr=>gr.orgId));

                let wrkPlace = _.map(_.filter(data,(item)=>{return item.unit == OrgUnit.WORKPLACE}),gr=>gr.orgId)
                vm.alreadySettingWorkplaces(wrkPlace.map(id => ({workplaceId: id, isAlreadySetting: true})));


            }).fail(error => {
                vm.$dialog.error(error).then(()=>{
                    if (error.messageId == "Msg_3240"){
                        vm.$jump("com", "/view/ccg/008/a/index.xhtml");
                    }
                });
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        openDialogCDL023() {
            const vm = this;
            let params: IObjectDuplication = {
                code: vm.currentWrkCode(),
                name: vm.currentWrkName(),
                targetType: TargetType.WORKPLACE,
                baseDate: new Date(),
                itemListSetting: vm.alreadySettingWorkplaces().map(w => w.workplaceId),
            };

            setShared("CDL023Input", params);
            // open modal
            modal('com', 'view/cdl/023/a/index.xhtml').onClosed(() => {
                let lstSelection: Array<string> = getShared("CDL023Output");
                if (!_.isEmpty(lstSelection)) {
                    let params: any;
                    let data:Array<any> = [];
                    let param = {
                        targetUnit: vm.unit(),
                        copySourceWkpId: vm.unit() == OrgUnit.WORKPLACE ? vm.selectedWkpId(): vm.selectedWkpGroupId(),
                        // shiftMasterCodes: _.map(self.shiftItems(), (val) => { return val.shiftMasterCode }),
                        copyDestinationWkpIds: lstSelection,
                        overWrite: true
                    }
                    vm.$blockui("show").then(() => {
                        vm.$ajax(PATH.copy, param)
                            .done((results: Array<CopySupportAllowOrgResult>) => {
                                let msg = '';
                                _.sortBy(results,(i)=>{return i.orgDisplayInfo.orgcCode}).forEach((result: CopySupportAllowOrgResult) => {
                                    let status = result.copyResult ? vm.$i18n('KMT013_15') : '';
                                    let destination = result.orgDisplayInfo.orgcCode + ' ' + result.orgDisplayInfo.orgName ;
                                    data.push({destination:destination,state: status});

                                });
                                let height = 30 + data.length *30;
                                let dialogHeight = (170 + height) > 500 ? 500 : (170+height);
                                let dialogSize = { width: 500, height: dialogHeight };
                                let gridHeight = height > 330 ? 330 : height;
                                vm.$window.modal('at', '/view/kmt/013/b/index.xhtml', {listdata: data,height: gridHeight},dialogSize).then(() => {
                                    vm.getAlreadySettingList();
                                });
                            }).fail(error => {
                            vm.$dialog.error(error);
                        }).always(() => {
                            vm.$blockui("hide");
                        });;
                    })


                }
            });
        }

        a4_2BtnClick() {
            const vm = this;
            if (vm.isWorkplaceGroupMode()) {
                vm.openDialogCDL014();
            } else {
                vm.openDialogCDL008();
            }
        }

        openDialogCDL008() {
            const vm = this;
            setShared('inputCDL008', {
                selectedCodes: _.map(vm.supportableList(),(item)=>{return item.id;}),
                baseDate: moment(new Date()).toDate(),
                isMultiple: true,
                selectedSystemType: 2,
                isrestrictionOfReferenceRange: true,
                showNoSelection: false,
                isShowBaseDate: true
            });
            modal('com',"/view/cdl/008/a/index.xhtml").onClosed(function(){
                let data = getShared('outputCDL008');
                let workplaceInfor = getShared('workplaceInfor');
                let baseDate = getShared('baseDateCDL008');
                if (data) {
                    vm.supportableList(_.sortBy(workplaceInfor,(item)=>{return item.code;}));
                }
            });
        }

        openDialogCDL014() {
            const vm = this;
            let data: any = {
                multiple: true,
                showEmptyItem: false,
                selectedMode: 3,
                alreadySettingList: _.map(vm.supportableList(), (item)=>{return item.id;}),
                currentCodes: _.map(vm.supportableList(), (item)=>{return item.code;}),
                currentNames: _.map(vm.supportableList(), (item)=>{return item.name;}),
                selectedWkpGroupTypes: []
            }
            vm.$window.modal('com','/view/cdl/014/a/index.xhtml', data)
                .then((result: any) => {
                    if (!result.isCanceled){
                        let workplaceInfor = _.map(result.result,(item:any)=>{
                            return new SupportableList(item.workPlaceId,item.workPlaceCode,item.workPlaceName);
                        });
                        vm.supportableList(_.sortBy(workplaceInfor,(item)=>{return item.code;}));
                    }

            });
        }

        a4_3BtnClick() {
            const vm = this;
            vm.supportableList(_.filter(vm.supportableList(),(item)=>{return vm.currentCodeList().indexOf(item.id)<0}))
        }

    }

    // Model Transfer
    interface IObjectDuplication {
        code: string;
        name: string;
        targetType: TargetType;
        itemListSetting: Array<string>;
        baseDate?: Date; // needed when target type: 職場 or 部門 or 職場個人 or 部門個人
        roleType?: number; // needed when target type: ロール
    }

    class SupportableList{
        id: string;
        code: string;
        name: string;
        constructor(id:string, code:string, name: string){
            this.id = id;
            this.code = code;
            this.name = name;
        }
    }

    class RegisterSupportAllowOrgCommand {
        /** 対象組織の単位 */
        orgUnit: number;

        /** 組織ID */
        orgId: string;

        /** List<応援可能組織> */
        orgCanBeSupports: Array<OrgCanBeSupportDto>;
        constructor(unit: number, orgId: string, orgCanBesupported: Array<OrgCanBeSupportDto>){
            this.orgUnit = unit;
            this.orgId = orgId;
            this.orgCanBeSupports = orgCanBesupported;
        }
    }

    class OrgCanBeSupportDto {
        orgId: string;
        constructor(orgId: string){
            this.orgId = orgId;
        }
    }

    class DeleteSupportAllowOrgCommand {
        /** 単位 */
        unit: number;

        orgId: string;

        constructor(unit: number, orgId: string){
            this.unit = unit;
            this.orgId = orgId;
        }
    }

    class CopySupportAllowOrgResult {
        workplaceId: string;
        copyResult: boolean;
        orgDisplayInfo: OrgDisplayInfoDto;
    }

    class OrgDisplayInfoDto {
        orgcCode: string;
        orgName: string;
    }

    class TargetOrgParams {
        baseDate: Moment;
        unit: number;
        orgId: string;
        constructor(date: Moment, unit: number, orgId: string){
            this.baseDate = date;
            this.unit = unit;
            this.orgId = orgId;
        }
    }

    interface TargetOrgInfo {
        /** 単位 */
        unit: number;
        /** 職場ID/ 職場グループID */
        orgId: string;
        /** 呼称 **/
        designation: string;
        /** コード **/
        code: string;
        /** 名称 **/
        name: string;
        /** 表示名 **/
        displayName: string;
        /** 呼称 **/
        genericTerm: string;

        supportableOrgInfoDtoList: Array<SupportableOrgInfoDto>;
    }

    interface SupportableOrgInfoDto {
        /** 単位 */
        unit: number;
        /** 職場ID/ 職場グループID */
        orgId: string;
        /** 呼称 **/
        designation: string;
        /** コード **/
        code: string;
        /** 名称 **/
        name: string;
        /** 表示名 **/
        displayName: string;
        /** 呼称 **/
        genericTerm: string;


    }
    interface SupportFuncGetOrganizationDto {
        unit: number;

        orgId: string;
    }
    class TargetType {
        // 雇用
        static EMPLOYMENT = 1;
        // 分類
        static CLASSIFICATION = 2;
        // 職位
        static JOB_TITLE = 3;
        // 職場
        static WORKPLACE = 4;
        // 部門
        static DEPARTMENT = 5;
    }
    enum OrgUnit{
        WORKPLACE = 0,
        WORKPLACEGROUP = 1
    }
}