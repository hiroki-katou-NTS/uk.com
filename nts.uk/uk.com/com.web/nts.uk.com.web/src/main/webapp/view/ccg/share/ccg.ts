module nts.uk.com.view.ccg.share.ccg {
    
    
    export class PersonModel {
        personId: string;

        personName: string;
    }

    import ListType = kcp.share.list.ListType;
    import TreeType = kcp.share.tree.TreeType;


    export interface GroupOption {
        /**
         * is Multi select.
         */
        isMultiSelect: boolean;
        
        onSearchAllClicked: (data: any) => void;
    }


    /**
    * Screen Model.
    */
    export class ListGroupScreenModel {
        isMultiple: boolean;
        tabs: KnockoutObservableArray<NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        selectedCode: KnockoutObservableArray<string>;
        baseDate: KnockoutObservable<Date>;
        employments: any;
        classifications: any;
        jobtitles: any;
        workplaces: any;
        onSearchAllClicked: (data: PersonModel) => void;
        

        constructor() {
            var self = this;
            self.isMultiple = false;
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

            self.classifications = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.Classification,
                selectedCode: self.selectedCode,
                isDialog: true
            }


            self.jobtitles = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: ListType.JOB_TITLE,
                selectedCode: this.selectedCode,
                isDialog: false,
                baseDate: self.baseDate,
            }

            self.workplaces = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                treeType: TreeType.WORK_PLACE,
                selectedCode: self.selectedCode,
                baseDate: self.baseDate,
                isDialog: false
            }



        }
        /**
         * Init component.
         */
        public init($input: JQuery, data: GroupOption): JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            var self = this;
            self.isMultiple = data.isMultiSelect;
            self.onSearchAllClicked = data.onSearchAllClicked;
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/ccg/share/ccg.xhtml').serialize();
            $input.load(webserviceLocator, function() {
                ko.cleanNode($input[0]);
                ko.applyBindings(self, $input[0]);
                $('#employeeList').ntsListComponent(self.employments);
                $('#classificationList').ntsListComponent(self.classifications);
                $('#jobtitleList').ntsListComponent(self.jobtitles);
                $(".accordion").accordion({
                    active: false,
                    collapsible: true
                });
                dfd.resolve();
            });

            return dfd.promise();
        }
        
        searchAllEmployee(): void{
            var self = this;
            service.findAllPerson().done(data=>{
                self.onSearchAllClicked(data);    
            });
        }
        

    }
    

    /**
    * Service,
    */
    export module service {

        // Service paths.
        var servicePath = {
            findAllPerson: "basic/person/getallperson"
        }

        /**
         * Find Employment list.
         */
        export function findAllPerson(): JQueryPromise<Array<PersonModel>> {
            return nts.uk.request.ajax('com', servicePath.findAllPerson);
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
        ntsGroupComponent(option: nts.uk.com.view.ccg.share.ccg.GroupOption): JQueryPromise<void>;
    }

    (function($: any) {
        $.fn.ntsGroupComponent = function(option: nts.uk.com.view.ccg.share.ccg.GroupOption): JQueryPromise<void> {

            // Return.
            return new nts.uk.com.view.ccg.share.ccg.ListGroupScreenModel().init(this, option);
        }

    } (jQuery));
}