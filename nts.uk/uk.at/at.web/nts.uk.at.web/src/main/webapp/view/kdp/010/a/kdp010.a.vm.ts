module nts.uk.at.view.kdp010.a {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import block = nts.uk.ui.block;
    import confirm = nts.uk.ui.dialog.confirm;
    import href = nts.uk.request.jump;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
		
        export class ScreenModel {
	
			constructor(){};

            public openGDialog(): void {
                nts.uk.ui.windows.sub.modal("/view/kdp/010/g/index.xhtml").onClosed(() => {
                	if(getShared("KDP010G")){
						setShared("KDP010G", false);
						//reLoad
					}
                });
            }

            public goToBScreen(): void {
                nts.uk.request.jump("/view/kdp/010/b/index.xhtml");
            }

			public goToKScreen(): void {
                nts.uk.request.jump("/view/kdp/010/k/index.xhtml");
            }

			public goToCScreen(): void {
                nts.uk.request.jump("/view/kdp/010/c/index.xhtml");
            }

            public goToDScreen(): void {
                nts.uk.request.jump("/view/kdp/010/d/index.xhtml");
            }

			public goToEScreen(): void {
                nts.uk.request.jump("/view/kdp/010/e/index.xhtml");
            }

			public goToFScreen(): void {
                nts.uk.request.jump("/view/kdp/010/f/index.xhtml");
            }
        }
    }

    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);    
    });
}