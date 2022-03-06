export interface UserLogin {
	userName: string
	password: string
}

export interface UserFullDetails extends UserLogin {
  email: string
}

export interface GameCard {
  name: String
  releasedDate: String
  backgroundImageUrl: String
  esrbRating: String
  genres: String[]
  gameId: number
}

export interface GameDetails extends GameCard {
  description: String
  metacriticRating: number
  metacriticUrl: String
  stores: String[]
  platforms: String[]
}

export interface ResponseMessage {
  status: string
}
