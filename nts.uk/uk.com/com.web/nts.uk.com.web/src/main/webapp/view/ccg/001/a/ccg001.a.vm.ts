module nts.uk.com.view.ccg001.a {
    import ListType = kcp.share.list.ListType;
    import TreeType = kcp.share.tree.TreeType;
    
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            selectedCode: KnockoutObservableArray<string>;
            baseDate: KnockoutObservable<Date>;
            employments: any;
            classifications: any;
            jobtitles: any;
            workplaces: any;
            constructor() {
                let self = this;
                self.selectedCode = ko.observableArray([]);
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
                self.employments = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.EMPLOYMENT,
                    selectedCode: self.selectedCode,
                    isDialog: true
                };
                $('#employeeList').ntsListComponent(this.employments);
                
                self.classifications = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.Classification,
                    selectedCode: self.selectedCode,
                    isDialog: true
                }
                
                 $('#classificationList').ntsListComponent(this.employments);
                
                self.jobtitles = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.JOB_TITLE,
                    selectedCode: this.selectedCode,
                    isDialog: false,
                    baseDate: self.baseDate,
                }
                
                $('#jobtitleList').ntsListComponent(self.jobtitles);
                
                self.workplaces = {
                    isShowAlreadySet: false,
                    isMultiSelect: false,
                    treeType: TreeType.WORK_PLACE,
                    selectedCode: self.selectedCode,
                    baseDate: self.baseDate,
                    isDialog: false
                }
                
                //$('#workplaces').ntsListComponent(self.workplaces);
            }

            public startPage(): JQueryPromise<any> {
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}