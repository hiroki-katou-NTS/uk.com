<template>
<div class="kafs00c" v-if="$application">
    <div class="card card-label" v-if="dispReason">
        <div class="card-header" style="align-items: center">
            <v-label class="border-0" v-bind:constraint="validations.reason">
                {{'KAFS00_16' | i18n}}</v-label>
            <span class="badge badge-warning" style="height: 30%">必須</span>
        </div>
        <div class="card-body">
            <div v-if="displayFixedReason">
                <span class="small-header">{{'KAFS00_17' | i18n}}</span>
                <nts-dropdown v-model="params.output.opAppStandardReasonCD">
                    <option v-for="(item, itemIndex) in $dropdownList" v-bind:key="itemIndex" v-bind:value="item.appStandardReasonCD">
                        {{item.opReasonForFixedForm | i18n}}
                    </option>
                </nts-dropdown>
            </div>
            <div v-if="displayAppReason">
                <span class="small-header">{{'KAFS00_18' | i18n}}</span>
                <nts-text-area　v-model="params.output.opAppReason" />
            </div>
        </div>
    </div>
</div>
</template>