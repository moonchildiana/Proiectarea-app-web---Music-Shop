import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  validateForm!: FormGroup;
  isSpinning = false;

  confirmationvalidator = (control:FormControl): {[s:string]:boolean} => {
    if(!control.value){
      return { required: true }
    } else if (control.value !== this.validateForm.controls['password'].value){
      return {confirm:true, error:true}
    }
    return {}
  }

constructor(private fb:FormBuilder,
  private authService: AuthService) { }

ngOnInit(){
  this.validateForm = this.fb.group({
    name: [null,[Validators.required]],
    email: [null,[Validators.required,Validators.email]],
    password: [null,[Validators.required]],
    confirmPassword: [null,[Validators.required, this.confirmationvalidator]]
  })
}

register(){
  console.log(this.validateForm.value);
  this.authService.register(this.validateForm.value).subscribe((res) => {
    console.log(res);
  })
}

}
