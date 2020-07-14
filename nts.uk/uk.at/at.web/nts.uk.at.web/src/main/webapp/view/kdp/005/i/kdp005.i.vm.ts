module nts.uk.at.view.kdp005.i {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
	export module viewmodel {
		export class ScreenModel {
            checks = ko.observable(true);
            timeStamp = ko.observable(1000);
            inforAuthent = ko.observable('打刻入力を利用することができません。');
			constructor() {
				let self = this;
			}
            
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.grayout();
//                let param = {pageNo:1};
//                service.getData(param).done(function(data) {
//                    console.log(data);
//                    if (data) {
//                        self.stampPageLayout().update(data);
//                        self.isDel(true);
//                    }
//                    $(document).ready(function() {
//                        $('#btnClose').focus();
//                    });
//                    dfd.resolve();
//                }).fail(function (res) {
//                    error({ messageId: res.messageId });
//                }).always(function () {
//                    block.clear();
//                });
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