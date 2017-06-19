module nts.uk.com.view.ccg001.a {
    import ListType = kcp.share.list.ListType;
    
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            selectedCode: KnockoutObservableArray<string>;
            date: KnockoutObservable<Date>;
            employments: any;
            classifications: any;
            constructor() {
                let self = this;
                self.date = ko.observable(new Date());
                self.selectedCode = ko.observableArray([]);
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
            }

            public startPage(): JQueryPromise<any> {
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}