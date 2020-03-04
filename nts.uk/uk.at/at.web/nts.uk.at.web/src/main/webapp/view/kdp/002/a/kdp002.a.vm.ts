module nts.uk.at.view.kdp002.a {
    
    export module viewmodel {
        
        export class ScreenModel {
            public startPage(): JQueryPromise<void>  {
               let self = this;
               var dfd = $.Deferred<void>();
                       let data: any = {
                        width: 200,
                        heighRow: 50,
                        maxColumn: 2,
                        clickProcess: self.clickProcess,
                        infoButton: [{
                            idButton: 'idA',
                            background: 'green',
                            textColor: 'red',
                            iconClass: 'img-link img-icon icon-download',
                            textSize: 18,
                            contentText: 'TextA'
                        },
                        {
                            idButton: 'idAA',
                            background: 'green',
                            textColor: 'red',
                            iconClass: 'img-link img-icon icon-download',
                            textSize: 18,
                            contentText: 'TextA'
                        },
                        {
                            idButton: 'idCCCC',
                            background: 'green',
                            textColor: 'red',
                            iconClass: 'img-link img-icon icon-download',
                            textSize: 18,
                            contentText: 'TextA',
                            buttonEmpty: true
                        },
                        {
                            idButton: 'idCCCC',
                            background: 'green',
                            textColor: 'red',
                            iconClass: 'img-link img-icon icon-download',
                            textSize: 18,
                            contentText: 'TextA',
                            buttonEmpty: true
                        },
                        {
                            background: '#8B7373',
                            textColor: 'red',
                            iconClass: 'img-link img-icon icon-download',
                            textSize: 18,
                            contentText: 'TextB',
                            idButton: 'idB',
                        },
                        {
                            background: 'gray',
                            textColor: 'red',
                            iconClass: 'img-link img-icon icon-download',
                            textSize: 18,
                            contentText: 'Textc',
                            idButton: 'idC',
                        }
                        ]
                    };
                $("#genText").ntsGridButton(data);
                dfd.resolve();
                return dfd.promise();
               }

           clickProcess(data: any) : any{
             let dfd = $.Deferred();
                console.log(data);
                let disable: boolean = false;
                if(data == 'TextA') disable = true;
                dfd.resolve({disable: disable});
                

              return dfd.promise();          
           }
        }
        
    }
}