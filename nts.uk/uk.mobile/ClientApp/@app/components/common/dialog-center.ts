import { component, Prop } from '@app/core/component';
import { Vue } from '@app/provider';

@component({
    template: `
        <div class="nts-dialog-center">
            <renderless-dialog-center :dialogs="dialogs" @updated="update">
            <div slot-scope="{ hide }">
                <transition v-for="dialog in dialogs" :key="dialog.id">
                    <div class="dialog-mask">
                        <div class="dialog-wrapper" @click.self.stop.prevent="hide(dialog)">
                        <div class="dialog-container">
                            <div class="dialog-header dialog-area">
                                <div class="dialog-title">{{ dialog.title }}</div>
                                <div class="close-area" v-if="dialog.noClose === false">
                                    <button class="close-button" @click="hide(dialog)">X</button>
                                </div>
                            </div>
                            <div class="dialog-content dialog-area">
                                {{ dialog.content }}
                            </div>
                            <div class="dialog-footer dialog-area"></div>
                        </div>
                        </div>
                    </div>
                </transition>
            </div>
            </renderless-dialog-center>
        </div>
    `
})
export class DialogCenter extends Vue {

    dialogs: Array<any>;

    update(val: any) {
        this.dialogs = val;
    }

    add(dialog: any) {
        this.dialogs.push(dialog);
    }
}

Vue.component('nts-dialog-center', DialogCenter);