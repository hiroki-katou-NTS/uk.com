module nts.uk.at.view.kmk008.b {
    export module viewmodel {
        export class ScreenModel {
            show: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            viewmodelC: any = new kmk008.c.viewmodel.ScreenModel();;
            constructor() {
                let self = this;
                self.show = ko.observable(true);
                self.show.subscribe(function(newVal) {
                    if (newVal)
                        $("#sidebar").ntsSideBar("show", 1);
                    else
                        $("#sidebar").ntsSideBar("hide", 1);
                });

                self.enable = ko.observable(true);
                self.enable.subscribe(function(newVal) {
                    if (newVal) {
                        $("#sidebar").ntsSideBar("enable", 1);
                        $("#sidebar").ntsSideBar("enable", 2);
                    }
                    else {
                        $("#sidebar").ntsSideBar("disable", 1);
                        $("#sidebar").ntsSideBar("disable", 2);
                    }
                });
                let self = this;
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-2');

            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            testSideMenu() {
                alert("clicked");
            }

            openSubWindow() {
                nts.uk.ui.windows.sub.modeless("/view/sample/sidebar/sidebar-sub.xhtml");
            }

            openNewTab() {
                window.open("/nts.uk.com.web/view/sample/sidebar/sidebar-sub.xhtml", "_blank").focus();
            }
        }

        export class ItemModel {
            code: number;
            name: string;
            constructor(code: number, name: string) {
                let self = this;
                self.code = code;
                self.name = name;
            }
        }
    }
}
