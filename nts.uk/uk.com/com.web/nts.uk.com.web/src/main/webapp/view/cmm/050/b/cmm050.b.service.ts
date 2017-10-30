module nts.uk.com.view.cmm050.b {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                testMailServerSetting: "sys/env/mailserver/testMailSetting",
            };
        
        /**
         * 
         */
        export function testMailServerSetting(data: model.MailServerTest): JQueryPromise<any> {
            return nts.uk.request.ajax(path.testMailServerSetting, data);
        }
    }
    
    /**
     * Model define.
     */
    export module model {
        export class MailServerTest {
            mailFrom: string;
            mailTo: string;
            contents: MailContents;
            
            constructor(mailFrom: string, mailTo: string, contents: MailContents){
              
                this.mailFrom = mailFrom;
                this.mailTo = mailTo;
                this.contents = contents;
            }
        }

        export class MailContents {
            subject: string;
            body: string;
            
            constructor(subject: string = "Mail test from server", body: string = "Hello, this is test message!!"){
                this.subject = subject;
                this.body = body;
            }
        }
    }
    
}