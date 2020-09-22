<template>
<div class="kafs08a mt-n2">
    <div class="step-wizard">
      <step-wizard 
      v-bind:items="['KAFS08_10', 'KAFS08_11', 'KAFS08_12']" 
      v-bind:selected="step" />
    </div>
    <kafs08a1 
    @nextToStepTwo="ProcessNextToStepTwo" 
    v-show="step == 'KAFS08_10'"
    v-bind:derpartureTime="derpartureTime"
    v-bind:returnTime="returnTime"
    v-bind:appReason="appReason"
    v-bind:params="params"/>
    <kafs08a2 
    @prevStepOne="ProcessPrevStepOne" 
    @nextToStepThree="ProcessNextToStepThree" 
    v-bind:departureTime="derpartureTime" 
    v-bind:returnTime="returnTime"  
    v-bind:comment="comment"
    v-bind:appReason="appReason"
    v-bind:table="achievementDetails"
    v-bind:businessTripInfoOutput="businessTripInfoOutput"
    v-bind:application="application"
    v-bind:listDate="listDate"
    v-show="step == 'KAFS08_11'" />
    <kafs08c v-show="step =='KAFS08_12'"
    v-bind:appID="appID"
    />
</div>
</template>