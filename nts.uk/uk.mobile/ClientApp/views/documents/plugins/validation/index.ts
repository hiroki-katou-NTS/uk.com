import { $ } from '@app/utils';
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
        textValue: {
            required: true
        },
        numberValue: {
            required: true
        }
    },
    constraints: [
        'nts.uk.shr.sample.primitive.strings.SampleStringAny',
        'nts.uk.shr.sample.primitive.strings.SampleStringKana'
    ]
})
export class DocumentsPluginsValidationComponent extends Vue {

    public textValue: string = 'nittsu';
    public numberValue: number = 1;

}