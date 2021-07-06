import React from 'react'
import Team from './Team'
import { Link } from 'react-router-dom'

const TeamDetail = ({team}) => {

    if (!team) {
        return <p>Loading...</p>
    }

    return (
        <div>
            <Team team = {Team} />
        </div>
    )
}
export default TeamDetail