<template>
<div class="kafs00b" v-if="$application">
    <div v-if="$appDispInfoStartupOutput.appDetailScreenInfo">
        <div class="card card-label">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_7' | i18n}}</v-label>
            </div>
            <div class="card-body mb-2">
                <span>{{ $application.employeeName | i18n }}</span> 
            </div>
        </div>   
        <div class="card card-label" v-if="displayPrePost">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_8' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body mb-2">
                <span>{{ prePostName | i18n }}</span> 
            </div>
        </div>
        <div class="card card-label">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_9' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body mb-2">
                <span>{{ dateText | i18n }}</span> 
            </div>
        </div>  
    </div>
    <div v-else>
        <div class="card card-label" v-if="displayPrePost">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_8' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <div style="width: 100%">
                    <nts-switchbox v-for="(option, optionIndex) in datasource" v-bind:key="optionIndex"
                        v-bind:disabled="!enablePrePost"
                        v-model="$application.prePostAtr" 
                        v-bind:value="option.code">
                            {{option.text | i18n}}
                    </nts-switchbox>
                </div>
            </div>
        </div>    
        <div class="card card-label">
            <div class="card-header" style="align-items: center">
                <v-label class="border-0 pl-0">
                    {{'KAFS00_9' | i18n}}</v-label>
                <span class="badge badge-warning" style="height: 30%">必須</span>
            </div>
            <div class="card-body">
                <div style="width: 100%" v-if="$application.useRangeDate">
                    <nts-switchbox v-for="(option, optionIndex) in datasource2" v-bind:key="optionIndex"
                        v-model="$application.isRangeDate" 
                        v-bind:value="option.code">
                            {{option.text | i18n}}
                    </nts-switchbox>
                </div>
                 <div v-if="!$application.isRangeDate">
                    <nts-date-input v-model="$application.appDate"/>
                </div>
                <div v-if="$application.isRangeDate">
                    <nts-date-range-input v-model="$application.dateRange" />
                </div>
            </div>
        </div>
    </div>
</div>
</template>