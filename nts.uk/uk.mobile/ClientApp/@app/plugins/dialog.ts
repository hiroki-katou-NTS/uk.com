import { Vue, VueConstructor } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { obj, dom, browser } from '@app/utils';
import { _ } from "@app/provider";

@component({
    template: `<div>
        <div class="dialog-header"> {{ title }}</div>
        <div class="dialog-body">
            <div class="text-left message"> {{ messageText }} </div>
            <div class="text-right message-id"> {{ messageId }} </div>
        </div>
        <div class="dialog-footer">    
            <button v-if="showClose" ref="close" class="btn btn-primary" v-on:click="closeHandler">toBeResource.close</button>
            <button v-if="showYes" ref="yes" class="btn btn-primary" v-on:click="yesHandler">toBeResource.yes</button>
            <button v-if="showNo" ref="no" class="btn btn-secondary" v-on:click="noHandler">toBeResource.no</button>
            <button v-if="showCancel" ref="cancel" class="btn btn-secondary" v-on:click="cancelHandler">toBeResource.cancel</button>
        </div>
    </div>`
})
class DialogComponent extends Vue {

    @Prop({
        required: true
    })
    message: string | Object;

    @Prop({
        default: 'info'
    })
    type: DialogType;
    
    @Prop({
        default: 'normal'
    })
    style: DialogStype;

    @Prop({})
    cancel: Function;

    @Prop({})
    yes: Function;

    @Prop({})
    no: Function;

    @Prop({
        default: () => {}
    })
    then: Function;

    title: string = "";
    messageText: string = "";
    messageId: string = "";
    showClose: boolean = false;
    showYes: boolean = false;
    showNo: boolean = false;
    showCancel: boolean = false;
    toBeResource = {
        yes: "はい",
        no: "いいえ",
        cancel: "キャンセル",
        close: "閉じる",
        info: "情報",
        warn: "警告",
        error: "エラー",
        confirm: "確認",
    };

    beforeCreate() {
        if(this.type !== 'confirm'){
            this.showClose = true;
        }
        if(this.type === 'confirm'){
            this.showYes = true;
            this.showNo = true;
            if(!_.isFunction(this.yes)) {
                this.yes = () => {};
            }
            if(!_.isFunction(this.no)) {
                this.no = () => {};
            }
            if(_.isFunction(this.cancel)){
                this.showCancel = true;
            }
        }
    }

    created() {
        if(_.isString(this.message)){
            this.messageText = this.message;
            this.messageId = "";
        }
        if(!_.isNil(this.message) && _.isPlainObject(this.message)){
            let messageX: string = (<any> this.message).message,
                messageIdX: string = (<any> this.message).messageId;

            if(!_.isEmpty(messageIdX) && _.isString(messageIdX)){
                this.messageId = messageIdX;

            }
            if(!_.isEmpty(messageX) && _.isString(messageX)){
                this.messageText = messageX;
            } else {
                this.messageText = this.$i18n(this.messageId, (<any> this.message).messageParams);
            }
        }

        if(this.type === "confirm") {
            this.title = this.toBeResource.confirm;
        } else if(this.type === "error") {
            this.title = this.toBeResource.error;
        } else if(this.type === "warn") {
            this.title = this.toBeResource.warn;
        } else {
            this.title = this.toBeResource.info;
        }

        if(this.style === "danger") {
            (<HTMLButtonElement>this.$refs.yes).classList.add("danger");
        }
    }

    closeHandler(){
        this.$close({ id: 100 });
        this.then();
    }

    yesHandler() {
        this.$close({ id: 100 });
        this.yes();
        this.then();
    }

    noHandler() {
        this.$close({ id: 100 });
        this.no();
        this.then();
    }

    cancelHandler() {
        this.$close({ id: 100 });
        this.cancel();
        this.then();
    }
}

export type DialogType = 'info' | 'error' | 'warn' | 'confirm';
export type DialogStype = 'normal' | 'process' | 'danger';

const dialog = {
    install(vue: VueConstructor<Vue>) {

        vue.prototype.$dialogError = (msg: string | object, option?: any) => {
            let dialog = DialogComponent;
            this.$modal(dialog, _.assignIn({ message: msg }, option, { type: 'error' }));
        };

        vue.prototype.$dialogInfo = (msg: string | object, option?: any) => {
            let dialog = DialogComponent;
            this.$modal(dialog, _.assignIn({ message: msg }, option, { type: 'info' }));
        };

        vue.prototype.$dialogWarn = (msg: string | object, option?: any) => {
            let dialog = DialogComponent;
            this.$modal(dialog, _.assignIn({ message: msg }, option, { type: 'warn' }));
        };

        vue.prototype.$dialogConfirm = (msg: string | object, option?: any) => {
            let dialog = DialogComponent;
            this.$modal(dialog, _.assignIn({ message: msg }, option, { type: 'confirm' }));
        };
    }
}

export { dialog };