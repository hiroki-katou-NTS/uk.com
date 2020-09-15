<template>
<div class="kafs08a mt-n2">
    <div class="step-wizard">
      <step-wizard 
      v-bind:items="['KAFS08_10', 'KAFS08_11', 'KAFS08_12']" 
      v-bind:selected="step" />
    </div>
    <kafs08a1 @nextToStepTwo="ProcessNextToStepTwo" v-if="step == 'KAFS08_10'" />
    <kafs08a2 
    @prevStepOne="ProcessPrevStepOne" 
    @nextToStepThree="ProcessNextToStepThree" 
    v-bind:departureTime="derpartureTime" 
    v-bind:returnTime="returnTime"  
    v-bind:comment="comment" 
    v-bind:table="achievementDetails"
    v-bind:businessTripInfoOutput="businessTripInfoOutput"
    v-if="step == 'KAFS08_11'" />
    <kafs08c v-if="step =='KAFS08_12'" />
</div>
</template>