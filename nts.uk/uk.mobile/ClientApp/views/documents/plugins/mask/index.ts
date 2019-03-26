import { Vue } from '@app/provider';
import { component, Watch } from '@app/core/component';


@component({
    route: {
        url: '/plugin/mask-layer',
        parent: '/documents'
    },
    template: require('./index.html'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    resource: {
    }
})
export class MaskLayerPluginDocument extends Vue {
    messages: string[] = [];

    timeout: number = 0;

    showMask() {
        let self = this;

        self.timeout = 5;
        self.messages.push('mask_show');

        self.$mask('show')
            .on(() => {
                self.messages.push('mask_click');
            }, () => {
                self.messages.push('mask_hide');
            });
    }

    @Watch('timeout')
    countdown(v: number) {
        let self = this;

        if (v >= 1) {
            setTimeout(() => {
                if (self.timeout > 0) {
                    self.timeout--;
                }
            }, 1000);
        } else {
            self.$mask('hide');
        }
    }


    showMaskOnce() {
        let self = this;

        self.timeout = 5;
        self.messages.push('mask_show_once');

        self.$mask('showonce')
            .on(() => {
                self.messages.push('mask_one_click');
            }, () => {
                self.timeout = 0;
                self.messages.push('mask_one_hide');
            });
    }
}