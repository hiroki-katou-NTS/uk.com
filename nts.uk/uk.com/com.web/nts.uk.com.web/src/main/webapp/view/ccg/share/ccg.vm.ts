module nts.uk.com.view.ccg.share.ccg {

    import ListType = kcp.share.list.ListType;
    import ComponentOption = kcp.share.list.ComponentOption;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import PersonModel = service.model.PersonModel;
    import GroupOption = service.model.GroupOption;
    import EmployeeSearchDto = service.model.EmployeeSearchDto;


    export module viewmodel {
        /**
        * Screen Model.
        */
        export class ListGroupScreenModel {
            isMultiple: boolean;
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            selectedCodeEmployment: KnockoutObservableArray<string>;
            selectedCodeClassification: KnockoutObservableArray<string>;
            selectedCodeJobtitle: KnockoutObservableArray<string>;
            selectedCodeWorkplace: KnockoutObservableArray<string>;
            selectedCodeEmployee: KnockoutObservableArray<string>;
            baseDate: KnockoutObservable<Date>;
            employments: ComponentOption;
            classifications: ComponentOption;
            jobtitles: ComponentOption;
            workplaces: TreeComponentOption;
            employeeinfo: ComponentOption;
            onSearchAllClicked: (data: PersonModel[]) => void;
            onSearchOnlyClicked: (data: PersonModel) => void;
            onSearchOfWorkplaceClicked: (data: PersonModel[]) => void;
            onSearchWorkplaceChildClicked: (data: PersonModel[]) => void;


            constructor() {
                var self = this;
                self.isMultiple = false;
                self.selectedCodeEmployment = ko.observableArray([]);
                self.selectedCodeClassification = ko.observableArray([]);
                self.selectedCodeJobtitle = ko.observableArray([]);
                self.selectedCodeWorkplace = ko.observableArray([]);
                self.selectedCodeEmployee = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.tabs = ko.observableArray([
                    {
                        id: 'tab-1',
                        title: nts.uk.resource.getText("CCG001_3"),
                        content: '.tab-content-1',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    },
                    {
                        id: 'tab-2',
                        title: nts.uk.resource.getText("CCG001_4"),
                        content: '.tab-content-2',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    }
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.reloadDataSearch();

            }
            /**
             * Init component.
             */
            public init($input: JQuery, data: GroupOption): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                self.isMultiple = data.isMutipleCheck;
                self.onSearchAllClicked = data.onSearchAllClicked;
                self.onSearchOnlyClicked = data.onSearchOnlyClicked;
                self.onSearchOfWorkplaceClicked = data.onSearchOfWorkplaceClicked;
                self.onSearchWorkplaceChildClicked = data.onSearchWorkplaceChildClicked;
                self.baseDate = data.baseDate;
                var webserviceLocator = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                    .mergeRelativePath('/view/ccg/share/ccg.xhtml').serialize();
                $input.load(webserviceLocator, function() {
                    ko.cleanNode($input[0]);
                    ko.applyBindings(self, $input[0]);
                    self.applyDataSearch();
                    $(".accordion").accordion({
                    active: false,
                    collapsible: true
                });   
                    dfd.resolve();
                });

                return dfd.promise();
            }

            searchAllEmployee(): void {
                var self = this;
                service.findAllPerson().done(data => {
                    self.onSearchAllClicked(data);
                });
            }

            toEmployeeDto(): EmployeeSearchDto {
                var self = this;
                var dto: EmployeeSearchDto = new EmployeeSearchDto();
                dto.baseDate = self.baseDate();
                dto.classificationCodes = self.selectedCodeClassification();
                dto.employmentCodes = self.selectedCodeEmployment();
                dto.jobTitleCodes = self.selectedCodeJobtitle();
                dto.workplaceCodes = self.selectedCodeWorkplace();
                return dto;
            }
            
            applyDataSearch(): void{
                var self = this;
                self.reloadDataSearch();
                $('#employmentList').ntsListComponent(self.employments);
                $('#classificationList').ntsListComponent(self.classifications);
                $('#jobtitleList').ntsListComponent(self.jobtitles);
                $('#workplaceList').ntsTreeComponent(self.workplaces);
            }
            
            searchDataEmployee(): void {
                var self = this;

                service.searchModeEmployee(self.toEmployeeDto()).done(data => {
                    self.employeeinfo = {
                        isShowAlreadySet: false,
                        isMultiSelect: self.isMultiple,
                        listType: ListType.EMPLOYEE,
                        employeeInputList: self.toUnitModelList(data),
                        selectType: SelectType.SELECT_BY_SELECTED_CODE,
                        selectedCode: self.selectedCodeEmployee,
                        isDialog: true,
                        isShowNoSelectRow: false,
                    }
                    $('#employeeinfo').ntsListComponent(self.employeeinfo);
                });

            }



            getEmployeeLogin(): void {
                var self = this;
                service.getPersonLogin().done(data => {
                    self.onSearchOnlyClicked(data);
                });
            }
            
            searchOfWorkplace(): void{
                var self = this;
                service.searchOfWorkplace(self.baseDate()).done(data=>{
                   self.onSearchOfWorkplaceClicked(data); 
                }).fail(function(error){
                    nts.uk.ui.dialog.alertError(error);
                });    
            }
            
            searchWorkplaceChild(): void{
                var self = this;
                service.searchWorkplaceChild(self.baseDate()).done(data=>{
                   self.onSearchOfWorkplaceClicked(data); 
                }).fail(function(error){
                    nts.uk.ui.dialog.alertError(error);
                });    
            }

            public toUnitModelList(dataList: PersonModel[]): KnockoutObservableArray<UnitModel> {
                var dataRes: UnitModel[] = [];

                for (var item: PersonModel of dataList) {
                    dataRes.push({
                        code: item.personId,
                        name: item.personName
                    });
                }
                return ko.observableArray(dataRes);
            }

            public toUnitModel(data: PersonModel): KnockoutObservable<UnitModel> {
                var dataRes: UnitModel = { code: data.personId, name: data.personName };
                return ko.observable(dataRes);
            }

            reloadDataSearch(){
                var self = this;
                 self.employments = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    selectType: SelectType.SELECT_ALL,
                    listType: ListType.EMPLOYMENT,
                    selectedCode: self.selectedCodeEmployment,
                    isDialog: true
                };

                self.classifications = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.Classification,
                    selectType: SelectType.SELECT_ALL,
                    selectedCode: self.selectedCodeClassification,
                    isDialog: true
                }


                self.jobtitles = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.JOB_TITLE,
                    selectType: SelectType.SELECT_ALL,
                    selectedCode: self.selectedCodeJobtitle,
                    isDialog: true,
                    baseDate: self.baseDate,
                }

                self.workplaces = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    treeType: TreeType.WORK_PLACE,
                    isShowSelectButton: true,
                    selectedWorkplaceId: self.selectedCodeWorkplace,
                    baseDate: self.baseDate,
                    isDialog: true
                }
            }

        }





        /**
         * Defined Jquery interface.
        */
        interface JQuery {

            /**
             * Nts list component.
             * This Function used after apply binding only.
             */
            ntsGroupComponent(option: nts.uk.com.view.ccg.share.ccg.service.model.GroupOption): JQueryPromise<void>;
        }

        (function($: any) {
            $.fn.ntsGroupComponent = function(option: nts.uk.com.view.ccg.share.ccg.service.model.GroupOption): JQueryPromise<void> {

                // Return.
                return new nts.uk.com.view.ccg.share.ccg.viewmodel.ListGroupScreenModel().init(this, option);
            }

        } (jQuery));
    }
}
    