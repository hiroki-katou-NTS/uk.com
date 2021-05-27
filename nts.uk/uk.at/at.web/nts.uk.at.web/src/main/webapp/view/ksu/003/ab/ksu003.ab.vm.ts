module nts.uk.at.view.ksu003.ab.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {

        // Screen Ab1
        width: KnockoutObservable<number>;
        tabIndex: KnockoutObservable<number | string>;
        filter: KnockoutObservable<boolean> = ko.observable(false);
        disabled: KnockoutObservable<boolean>;
        workplaceIdKCP013: KnockoutObservable<string> = ko.observable('');
        selected: KnockoutObservable<string>;
        dataSources: KnockoutObservableArray<any>;

        // Screen Ab2
        //list hyper link
        textButtonArrComPattern: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedButtonTableCompany: KnockoutObservable<any> = ko.observable({});
        sourceCompany: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() { //id : workplaceId || workplaceGroupId; 
            let self = this;
            // Screen Ab1
            self.width = ko.observable(875);
            self.tabIndex = ko.observable('');
            self.disabled = ko.observable(false);
            self.selected = ko.observable();
            self.dataSources = ko.observableArray([]);

            // Screen Ab2
            let arr = [];
            for (let i = 0; i < 9; i++) {
                arr.push({ name: [i], id: [i] + 'id', formatter: _.escape });
                self.textButtonArrComPattern.push(new HyperLink(i, 'id' + i));
            }
            self.contextMenu = [
                { id: "openDialog", text: getText("KSU001_1705")  },
                { id: "openPopup", text: getText("KSU001_1706")},
                { id: "delete", text: getText("KSU001_1707") }
            ];
             
            let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
            self.sourceCompany(source);
            console.log('a');
        }

        showHideAb() {
            $("#screen-Ab1").hide();
            $("#screen-Ab2").show();
        }
            openC(){
            nts.uk.ui.windows.sub.modal('/view/ksu/003/c/index.xhtml').onClosed(() => {
                
            });
            
        }
        
        // Screenb1

        // Screen Ab2
    }
}
class HyperLink {
    name: string;
    id: string;

    constructor(name: string, id: string) {
        this.name = name;
        this.id = id;

    }
}