module nts.uk.at.view.kdp010.i {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
    import confirm = nts.uk.ui.dialog.confirm;
	export module viewmodel {
		export class ScreenModel {
            stampPageLayout = ko.observable(new StampPageLayout());
            isDel = ko.observable(false);
			constructor() {
				let self = this;
			}
			public startPage(): JQueryPromise<any> {
				let self = this;
                let dfd = $.Deferred();
                block.grayout();
                let param = {pageNo:1};
                service.getData(param).done(function(data) {
//                    console.log(data);
                    if (data) {
                        self.stampPageLayout().update(data);
                        self.isDel(true);
                    }
                    $(document).ready(function() {
                        $('#pageComment').focus();
                    });
                    dfd.resolve();
                }).fail(function (res) {
                    error({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
                return dfd.promise();
			}
            
			public save() {
				let self = this;
                if($root.errors.isEmpty){
                    block.grayout();
                    service.save(ko.toJS(self.stampPageLayout())).done(function() {
                        self.isDel(true);
                        info({ messageId: "Msg_15" }).then(()=>{
                            $(document).ready(function() {
                                $('#pageComment').focus();
                            });    
                        });
                    }).fail(function (res) {
                        error({ messageId: res.messageId }).then(()=>{
                            $(document).ready(function() {
                                $('#pageComment').focus();
                            });
                        });
                    }).always(function () {
                        block.clear();
                    });
                }
			}
            
			public deleteSetting() {
				let self = this;
                confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    block.grayout();
                    service.del().done(function() {
                        self.stampPageLayout(new StampPageLayout());
                        self.isDel(false);
                        info({ messageId: "Msg_16" }).then(()=>{
                            $(document).ready(function() {
                                $('#pageComment').focus();
                            });    
                        });
                    }).fail(function (res) {
                        error({ messageId: res.messageId });
                    }).always(function () {
                        block.clear();
                    });
                }).ifNo(()=>{
                    $('#pageComment').focus();
                });
			}
            
            public closeDialog(): void {
				nts.uk.ui.windows.close();
			}
		}

        class StampPageLayout {
            pageNo = 1;
            stampPageName = getText("KDP010_236");
            stampPageComment = new StampPageComment();
            buttonLayoutType = 0;
            lstButtonSet = [];
            btn1 = new ButtonSettings();
            btn2 = new ButtonSettings();
            btn3 = new ButtonSettings();
            btn4 = new ButtonSettings();
            btn5 = new ButtonSettings();
            btn6 = new ButtonSettings();
            constructor(){
                let self = this;
            }
            update(data?:any){
                let self = this;
                if(data){
                    self.stampPageComment.update(data.stampPageComment);
                    _.forEach(data.lstButtonSet, (item) => {
                        if(item.buttonPositionNo == 1){
                            self.btn1.update(item);
                            self.lstButtonSet.push(self.btn1);
                        }else if(item.buttonPositionNo == 2){
                            self.btn2.update(item);
                            self.lstButtonSet.push(self.btn2);
                        }else if(item.buttonPositionNo == 3){
                            self.btn3.update(item);
                            self.lstButtonSet.push(self.btn3);
                        }else if(item.buttonPositionNo == 4){
                            self.btn4.update(item);
                            self.lstButtonSet.push(self.btn4);
                        }else if(item.buttonPositionNo == 5){
                            self.btn5.update(item);
                            self.lstButtonSet.push(self.btn5);
                        }else if(item.buttonPositionNo == 6){
                            self.btn6.update(item);
                            self.lstButtonSet.push(self.btn6);
                        }
                    });
                }
            }
            
            setting(buttonPositionNo: number){
                let self = this;
                let dataI = {
                    dataShare: ko.toJS(self),
                    buttonPositionNo: buttonPositionNo
                }
                nts.uk.ui.windows.setShared('KDP010_G', dataI);
                nts.uk.ui.windows.sub.modal("/view/kdp/010/h/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared('KDP010_H');
                    if (data) {
                        _.forEach(data.dataShare.lstButtonSet, (item) => {
                            let buttonSet = _.find(self.lstButtonSet,['buttonPositionNo',item.buttonPositionNo]);
                            if(buttonSet){
                                buttonSet.update(item);
                            }else if(item.buttonPositionNo == 1){
                                self.btn1.update(item);
                                self.lstButtonSet.push(self.btn1);
                            }else if(item.buttonPositionNo == 2){
                                self.btn2.update(item);
                                self.lstButtonSet.push(self.btn2);
                            }else if(item.buttonPositionNo == 3){
                                self.btn3.update(item);
                                self.lstButtonSet.push(self.btn3);
                            }else if(item.buttonPositionNo == 4){
                                self.btn4.update(item);
                                self.lstButtonSet.push(self.btn4);
                            }else if(item.buttonPositionNo == 5){
                                self.btn5.update(item);
                                self.lstButtonSet.push(self.btn5);
                            }else if(item.buttonPositionNo == 6){
                                self.btn6.update(item);
                                self.lstButtonSet.push(self.btn6);
                            }
                        }); 
                    }
                    $('#btnSave').focus();
                });    
            }
        }
        
        class StampPageComment{
            pageComment = ko.observable("");
            commentColor = ko.observable("#000000");
            constructor(){
                let self = this;
            }
            update(data?:any){
                let self = this;
                if(data){
                    self.pageComment(data.pageComment);
                    self.commentColor(data.commentColor);
                }
            }
        }
        
        class ButtonSettings {
            buttonPositionNo: number;
            buttonDisSet = new ButtonDisSet();
            buttonType = 0;
            usrArt = ko.observable(0);
            audioType = 0;
            constructor(){
                let self = this;
            }
            update(param: any){
                let self = this;
                if(param){
                    self.buttonPositionNo = param.buttonPositionNo;
                    self.buttonDisSet.update(param.buttonDisSet);
                    self.buttonType = param.buttonType;
                    self.usrArt(param.usrArt);
                    self.audioType = param.audioType;
                }
            }
        }
        
        class ButtonDisSet{
            backGroundColor = ko.observable('');
            buttonNameSet = new ButtonNameSet();
            constructor(){
                let self = this;
            }
            update(param: any){
                let self = this;
                self.backGroundColor(param.backGroundColor);
                self.buttonNameSet.update(param.buttonNameSet);
            }
        }
        
        class ButtonNameSet{
            buttonName = ko.observable('');
            textColor = ko.observable('');
            constructor(){
                let self = this;
            }
            update(param: any){
                let self = this;
                self.buttonName(param.buttonName);
                self.textColor(param.textColor);
            }
        }
    }
}