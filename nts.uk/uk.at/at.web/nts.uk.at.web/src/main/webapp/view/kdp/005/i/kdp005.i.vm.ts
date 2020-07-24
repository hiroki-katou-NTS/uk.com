module nts.uk.at.view.kdp005.i {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
	export module viewmodel {
		export class ScreenModel {
            time = getShared('resultDisplayTime')
            checks = ko.observable(this.time > 0);
            timeStamp = ko.observable(this.time);
            inforAuthent = getShared('errorMessage');
			constructor() {
				let self = this;
			}
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.grayout();
                $(document).ready(function() {
                    $('#btnClose').focus();
                });
                self.countDown();
                block.clear();
                dfd.resolve();
                return dfd.promise();
            }
            
            countDown(){
                let self =  this;
                let run = setInterval(()=>{
                    if(self.timeStamp() == 0){
                        clearInterval(run);
                        self.closeDialog();
                    }
                    self.timeStamp(self.timeStamp() - 1);    
                }, 1000);
            }
            
            public closeDialog(): void {
				nts.uk.ui.windows.close();
			}
        }
    }
}