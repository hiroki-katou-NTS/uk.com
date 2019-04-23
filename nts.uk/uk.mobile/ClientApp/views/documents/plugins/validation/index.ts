import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    name: 'documentspluginsvalidation',
    route: {
        url: '/plugins/validation',
        parent: '/documents'
    },
    template: require('./index.vue'),
    resource: require('./resources.json'),
    markdown: {
        vi: require('./content/vi.md'),
        jp: require('./content/jp.md')
    },
    validations: {
        model: {
            username: {
                required: true,
                custom_validator: {
                    test: /\d{3,5}/,
                    message: '{0} is number in range [100, 99999].'
                }
            },
            password: {
                required: true
            }
        }
    }
})
export class DocumentsPluginsValidationComponent extends Vue {
    public model: {
        username: string;
        password: string;
    } = {
        username: 'username',
        password: 'password'
    };
}