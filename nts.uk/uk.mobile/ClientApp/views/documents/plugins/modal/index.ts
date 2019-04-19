import { Vue } from '@app/provider';
import { component } from '@app/core/component';
import { ModalComponent } from './modal-component';
@component({
    name: 'documentspluginsmodal',
    route: {
        url: '/plugins/modal',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    components: {
        'sample': ModalComponent
    }
})
export class DocumentsPluginsModalComponent extends Vue {
    name: string = 'Nittsu System Viet Nam';

    showModal() {
        let name = this.name;

        this.$modal('sample', { name })
            .then(v => {
                alert(`You are choose: ${v}`);
            });
    }

    showWarn() {
        this.$modal.warn('Warning, this is warning message!')
            .then(() => {
                alert('Clicked: close');
            });
    }

    showInfo() {
        this.$modal.info('Info, this is info message!')
            .then(() => {
                alert('Clicked: close');
            });
    }

    showError() {
        this.$modal.error('Info, this is info message!')
            .then(() => {
                alert('Clicked: close');
            });
    }

    showConfirm() {
        this.$modal.confirm('Info, this is info message!', 'danger')
            .then(v => {
                alert('Clicked: ' + v);
            });
    }
}