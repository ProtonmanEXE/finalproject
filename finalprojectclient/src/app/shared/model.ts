export interface UserLogin {
	userName: string
	password: string
}

export interface UserFullDetails extends UserLogin {
	handphone:string
  email: string
}

export interface Todo {

}

export interface ResponseMessage {
  status: string
}
