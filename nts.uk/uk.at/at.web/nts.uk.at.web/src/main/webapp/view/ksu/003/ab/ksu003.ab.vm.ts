module nts.uk.at.view.ksu003.ab.viewmodel {
    import setShare = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
		
		// Screen Ab1
        tabIndex: KnockoutObservable<number | string>;
        filter: KnockoutObservable<boolean> = ko.observable(false);
        disabled: KnockoutObservable<boolean>;
        workplaceIdKCP013: KnockoutObservable<string> = ko.observable('');
        selected: KnockoutObservable<string>;
        dataSources: KnockoutObservableArray<any>;

		// Screen Ab2

        constructor() { //id : workplaceId || workplaceGroupId; 
            let self = this;
			// Screen Ab1
            self.tabIndex = ko.observable('');
            self.disabled = ko.observable(false);
            self.selected = ko.observable();
            self.dataSources = ko.observableArray([]);

			// Screen Ab2
        }
		
		showHideAb(){
			$("#screen-Ab1").hide();
			$("#screen-Ab2").show();
		}
		
		openC(){
			nts.uk.ui.windows.sub.modal('/view/ksu/003/c/index.xhtml').onClosed(() => {
				
			});
			
		}
		
		// Screen Ab1
		
		// Screen Ab2
    }
}